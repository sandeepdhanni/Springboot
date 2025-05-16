package com.example.ReactiveProgramming.repository;

import com.example.ReactiveProgramming.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {
    Flux<Customer> findByNameContains(String name);
}

