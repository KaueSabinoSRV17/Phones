package com.api.phones.service;

import com.api.phones.model.Phone;
import com.api.phones.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneService {
    @Autowired
    private PhoneRepository repository;

    public List<Phone> findAllPhones() {
        return repository.findAll();
    }

    public Optional<Phone> findPhoneById(String id) {
        Optional<Phone> response = repository.findById(Integer.parseInt(id));
        return response.isPresent() ? response : Optional;
    }

    public Phone savePhoneOnDataBase(Phone phone) {
        repository.save(phone);
        return phone;
    }

}
