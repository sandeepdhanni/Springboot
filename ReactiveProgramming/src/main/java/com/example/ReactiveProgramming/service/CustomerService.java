package com.example.ReactiveProgramming.service;

import com.example.ReactiveProgramming.entity.Customer;
import com.example.ReactiveProgramming.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public Flux<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Mono<Customer> getById(Long id) {
        return customerRepository.findById(id);
    }

    public Mono<Customer> save(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    public Mono<Void> delete(Long id) {
        return customerRepository.deleteById(id);
    }


    public Mono<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

}
