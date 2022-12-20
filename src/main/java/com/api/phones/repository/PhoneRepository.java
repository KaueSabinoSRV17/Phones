package com.api.phones.repository;

import com.api.phones.model.Phone;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Integer> {
    boolean existsByName(String name);
}
