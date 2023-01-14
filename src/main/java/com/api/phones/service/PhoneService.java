package com.api.phones.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.phones.exceptions.DomainException;
import com.api.phones.model.Phone;
import com.api.phones.repository.PhoneRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PhoneService {

    @Autowired
    private PhoneRepository repository;

    public Page<Phone> findAllPhones(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Phone> findPhoneById(Integer id) {
        Optional<Phone> response = repository.findById(id);
        return response.isPresent() ? response : Optional.empty();
    }

    public Optional<Phone> savePhoneOnDataBase(Phone newPhone) {
        if (existsByName(newPhone.getName())) {
            throw new DomainException("We Already Have a phone with that NAME!");
        }
        repository.save(newPhone);        
        return repository.findById(newPhone.getId());
    }

    public void delete(Integer id) {
        if (!existsById(id)) {
            throw new DomainException("This Phone Does not Exists!");
        }
        repository.deleteById(id);
    }

    public Optional<Phone> updatePhoneOnDataBase(Phone phone, Integer id) {
        boolean phoneExists = existsById(id);
        if (!phoneExists) {
            throw new DomainException("This Phone Does not Exists yet!");
        }
        phone.setId(id);
        return savePhoneOnDataBase(phone);
    }

    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

}
