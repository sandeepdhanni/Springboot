package com.example.TranasactionEg.repository;

import com.example.TranasactionEg.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Product,Integer> {
}
