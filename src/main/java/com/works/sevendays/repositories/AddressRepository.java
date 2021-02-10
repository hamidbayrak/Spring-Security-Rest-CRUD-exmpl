package com.works.sevendays.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.works.sevendays.entitiies.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
