package com.works.sevendays.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.works.sevendays.entitiies.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
