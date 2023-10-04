package com.sailor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sailor.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
