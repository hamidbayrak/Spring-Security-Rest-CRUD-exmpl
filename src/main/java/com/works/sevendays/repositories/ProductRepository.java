package com.works.sevendays.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.works.sevendays.entitiies.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	

}
