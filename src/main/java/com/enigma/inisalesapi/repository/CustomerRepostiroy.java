package com.enigma.inisalesapi.repository;

import com.enigma.inisalesapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepostiroy extends JpaRepository<Customer,String> {
}
