package com.example.TranasactionEg.repository;

import com.example.TranasactionEg.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
