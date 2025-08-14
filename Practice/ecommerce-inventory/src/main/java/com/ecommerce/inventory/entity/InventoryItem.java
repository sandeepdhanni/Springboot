package com.ecommerce.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "Inventory_Items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class  InventoryItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String productCode;
    private String name;

    private int totalQuantity;
    private int reservedQuantity;
}
