package com.ecommerce.inventory.controller;

import com.ecommerce.inventory.dto.InventoryRequestDto;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.inventory.service.InventoryService;

import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@RequestMapping("/product")
public class InventoryController {

    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/supply-item")
    public ResponseEntity<String> supplyItem(@RequestBody InventoryRequestDto request) {

        log.info("supplyItem started");
        String result = inventoryService.supplyItem(request);
        if (!StringUtils.isBlank(result)) {
            log.info("returning result");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            log.error("returning bad request result");
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/reserve-items")
    public ResponseEntity<String> processReserveItems(@RequestParam String productCode
            , @RequestParam int reserveItemCount) {

        log.info("processReserveItems started with : productcode : {} , reserveItemCount :{} ", productCode, reserveItemCount);
        String res = inventoryService.processReserveItems(productCode, reserveItemCount);

        if (!StringUtils.isBlank(res)) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("not reserved", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/cancel")
    public CompletableFuture<ResponseEntity<String>> cancel(@RequestParam String productCode, @RequestParam int quantity) {
        return inventoryService.cancelReservation(productCode, quantity)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/availability")
    public CompletableFuture<ResponseEntity<String>> availability(@RequestParam String productCode) {
        return inventoryService.queryAvailability(productCode)
                .thenApply(ResponseEntity::ok);
    }
}
