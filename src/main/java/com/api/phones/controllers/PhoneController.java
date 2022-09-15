package com.api.phones.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.phones.dto.PhoneDTO;
import com.api.phones.model.Phone;
import com.api.phones.repository.PhoneRepository;
import com.api.phones.service.PhoneService;


@RestController
@RequestMapping("/phones")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PhoneController {
    
    @Autowired
    private PhoneService service;

    @GetMapping
    public ResponseEntity<Page<Phone>> listOfPhones(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllPhones(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Phone> phoneById(@PathVariable Integer id) {
        return service.findPhoneById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
                
    }

    @PostMapping
    public ResponseEntity<?> saveNewPhone(@RequestBody @Valid PhoneDTO phone) {
        if (service.existsByName(phone.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: We Already Have a Phone with that NAME.");
        }
        var newPhone = new Phone(phone);
        Optional<Phone> savedPhone = service.savePhoneOnDataBase(newPhone);
        return savedPhone
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.internalServerError().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Phone> deletePhone(@PathVariable("id") Integer id) {
        Optional<Phone> deletedPhone = service.delete(id);
        return deletedPhone
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePhone(@RequestBody @Valid PhoneDTO phone, @PathVariable("id") Integer id) {
        Optional<Phone> phoneToBeUpdated = service.findPhoneById(id);
        if (phoneToBeUpdated.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: We Dont have that phone yet. You should just save it!");
        }
        var updatedPhone = new Phone();
        BeanUtils.copyProperties(phone, updatedPhone);
        updatedPhone.setId(phoneToBeUpdated.get().getId());
        return service.savePhoneOnDataBase(updatedPhone)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.internalServerError().build());
    }
    
}
