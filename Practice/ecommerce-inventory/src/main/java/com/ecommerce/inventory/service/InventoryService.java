package com.ecommerce.inventory.service;

import com.ecommerce.inventory.dto.InventoryRequestDto;
import com.ecommerce.inventory.entity.InventoryItem;
import com.ecommerce.inventory.repository.InventoryItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryItemRepository inventoryRepo;

    @Autowired
    private InventoryTransactionService inventoryTransactionService;

  //  private RedisTemplate<String, Object> redisTemplate;


    public String supplyItem(InventoryRequestDto request) {
        log.info("supplyItem started");
        if (!ObjectUtils.isEmpty(request)) {

            InventoryItem inventoryItem = new InventoryItem();
            inventoryItem.setProductCode(request.getProductCode());
            inventoryItem.setName(request.getName());
            inventoryItem.setTotalQuantity(request.getQuantity());
            inventoryItem.setReservedQuantity(0);
            InventoryItem savedInventory = inventoryRepo.save(inventoryItem);

            if (!ObjectUtils.isEmpty(savedInventory)) {
                return "Inventory Saved Successfully";
            }
        } else {
            log.error("no data present in  request object ");
            throw new RuntimeException("request  is empty");
        }
        return null;

    }

    public String processReserveItems(String productCode, int reserveItemCount) {

        log.info("processReserveItems started");
        InventoryItem inventoryItem = inventoryRepo.findByProductCode(productCode).orElseThrow(() -> new RuntimeException("Product not found this code : " + productCode));

        if (!ObjectUtils.isEmpty(inventoryItem)) {
            if (inventoryItem.getTotalQuantity() < reserveItemCount) {
                throw new RuntimeException("quantity not avaialble  ; available quantity is :" + inventoryItem.getTotalQuantity());
            } else {
                int newCount = inventoryItem.getTotalQuantity() - reserveItemCount;

                inventoryItem.setReservedQuantity(inventoryItem.getReservedQuantity() + reserveItemCount);
                inventoryItem.setTotalQuantity(newCount);
                InventoryItem updatedInventory = inventoryRepo.save(inventoryItem);
                if (!ObjectUtils.isEmpty(updatedInventory)) {
                    return "intem reserved suucessfully " + reserveItemCount;

                } else {
                    log.error("item not reserved");
                    throw new RuntimeException("Items not reverved");
                }
            }
        }
        return null;

    }

    // Cancel Reservation
    public CompletableFuture<String> cancelReservation(String productCode, int quantity) {
        return CompletableFuture.supplyAsync(() -> inventoryTransactionService.cancelReservationSync(productCode, quantity));
    }

    // Query Availability
    @Cacheable(key = "#productCode", value = "inventoryitem")
    public CompletableFuture<String> queryAvailability(String productCode) {
        return CompletableFuture.supplyAsync(() -> {
            InventoryItem item = inventoryRepo.findByProductCode(productCode)
                    .orElseThrow(() -> new RuntimeException("Item not found"));

            return "Available quantity for product " + productCode + " is: " + item.getTotalQuantity();
        });
    }


}
