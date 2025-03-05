package com.example.repo;

import com.example.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepo extends JpaRepository<Orders,Integer> {
    Orders findByRazorPayOrderId(String razorPayOrderId);
}
