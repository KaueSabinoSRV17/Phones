package com.api.phones.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.phones.model.Phone;
import com.api.phones.repository.PhoneRepository;

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

    public Optional<Phone> savePhoneOnDataBase(Phone phone) {
        repository.save(phone);
        Optional<Phone> isItReallySaved = repository.findById(phone.getId());
        return isItReallySaved.isPresent() ? isItReallySaved : Optional.empty();
    }

    public Optional<Phone> delete(Integer id) {
        Optional<Phone> doestItActuallyExists = findPhoneById(id);
        if (!doestItActuallyExists.isPresent()) {
            return Optional.empty();
        }
        repository.deleteById(id);
        return doestItActuallyExists;
    }

    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

}
