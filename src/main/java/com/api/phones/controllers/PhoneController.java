package com.api.phones.controllers;

import javax.validation.Valid;

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
import com.api.phones.service.PhoneService;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/phones")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class PhoneController {
    
    @Autowired
    private PhoneService service;

    @GetMapping
    public ResponseEntity<Page<Phone>> listOfPhones(Pageable pageable) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(service.findAllPhones(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Phone> phoneById(@PathVariable Integer id) {
        return service.findPhoneById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
                
    }

    @PostMapping
    public ResponseEntity<?> saveNewPhone(@RequestBody @Valid PhoneDTO phone) {
        return service.savePhoneOnDataBase(new Phone(phone))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.internalServerError().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhone(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePhone(@RequestBody @Valid PhoneDTO phone, @PathVariable Integer id) {        
        return service.updatePhoneOnDataBase(new Phone(phone), id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.internalServerError().build());
    }
    
}
