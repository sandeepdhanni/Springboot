package com.example.CRUDRepo.repository;

import com.example.CRUDRepo.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepo extends CrudRepository<Product,Long> {

    @Query(value = "SELECT * FROM Product p WHERE p.price>?1",nativeQuery = true)
    List<Product> findProductByPriceGreaterThan(Double price);


    @Query(value = "SELECT * FROM Product p WHERE name=?1",nativeQuery = true)
    Product findProductByName(String name);

    @Query(value = "SELECT COUNT(*) FROM Product")
    Long countProducts();


}
