package com.ecommerce.inventory.service;

import com.ecommerce.inventory.entity.InventoryItem;
import com.ecommerce.inventory.repository.InventoryItemRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InventoryTransactionService {

    @Autowired
    public InventoryItemRepository inventoryRepo;


    @Transactional
    public String cancelReservationSync(String productCode, int quantity) {
        InventoryItem item = inventoryRepo.findByProductCodeForUpdate(productCode)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (item.getReservedQuantity() < quantity) {
            throw new RuntimeException("Not enough reserved quantity to cancel.");
        }
        int exeistReservedQuantity = item.getReservedQuantity() - quantity;
        item.setReservedQuantity(exeistReservedQuantity);
        item.setTotalQuantity(item.getTotalQuantity() + quantity);
        inventoryRepo.save(item);
        return "Cancelled " + quantity + " units for product: " + productCode;
    }
}
