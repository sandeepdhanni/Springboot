package com.javatechie.repository;

import com.javatechie.entity.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEventRepository extends JpaRepository<OrderEvent,String> {
}
