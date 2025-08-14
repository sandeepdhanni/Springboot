package com.ecommerce.inventory.repository;

import com.ecommerce.inventory.entity.InventoryItem;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem,Integer> {

     Optional<InventoryItem> findByProductCode(String productCode);

     @Lock(LockModeType.PESSIMISTIC_WRITE)
     @Query("SELECT i FROM InventoryItem i WHERE i.productCode = :productCode")
     Optional<InventoryItem> findByProductCodeForUpdate(@Param("productCode") String productCode);


}
