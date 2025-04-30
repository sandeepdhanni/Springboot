package com.example.TranasactionEg.service;

import com.example.TranasactionEg.entity.Order;
import com.example.TranasactionEg.entity.Product;
import com.example.TranasactionEg.handler.InventoryHandler;
import com.example.TranasactionEg.handler.OrderHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderProcessingService {

    private final OrderHandler orderHandler;

    private final InventoryHandler inventoryHandler;

    public OrderProcessingService(OrderHandler orderHandler, InventoryHandler inventoryHandler) {
        this.orderHandler = orderHandler;
        this.inventoryHandler = inventoryHandler;
    }

    //isolation means it controls how one transaction made changes made by another transaction
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    public Order placeAnOrder(Order order){

        //get product inventory
        Product product=inventoryHandler.getProduct(order.getProductId());

        //validate stock availability
        validateStockAvailability(order, product);

        //update total price in the order entity.
        order.setTotalPrice(order.getQuantity()* product.getPrice());

        //save order
        Order saveOrder = orderHandler.saveOrder(order);

        //update stock in inventory
        updateInventoryStock(order, product);

        return saveOrder;
    }

    private static void validateStockAvailability(Order order, Product product) {
        if(order.getQuantity() > product.getStockQuantity()){
            throw new RuntimeException("Insufficient stocks");
        }
    }

    private void updateInventoryStock(Order order, Product product) {
        int availableStock = product.getStockQuantity() - order.getQuantity();
        product.setStockQuantity(availableStock);
        inventoryHandler.updateProductDetails(product);
    }

}
