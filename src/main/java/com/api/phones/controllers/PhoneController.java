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
import org.springframework.web.bind.annotation.RestController;

import com.api.phones.dto.PhoneDTO;
import com.api.phones.model.Phone;
import com.api.phones.service.PhoneService;
import com.api.utils.StringUtils;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class PhoneController {
    
    @Autowired
    private PhoneService service;

    @GetMapping("/")
    public String home() {
        StringBuilder lista = new StringBuilder("<ul>");
        String title = StringUtils.htmlElement("h1", "Welcome to the Phone API!");
        lista.append(StringUtils.htmlElement("li", "/phones: Returns all the phones"));
        lista.append(StringUtils.htmlElement("li", "/phone/{id}: Returns a phone by ID"));
        return title + lista;
    }

    @GetMapping("/phones")
    public ResponseEntity<Page<Phone>> listOfPhones(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllPhones(pageable));
    }

    @GetMapping("/phone/{id}")
    public ResponseEntity<Object> phoneById(@PathVariable("id") Integer id) {
        return service.findPhoneById(id).isPresent()
            ? ResponseEntity.status(HttpStatus.FOUND).body(service.findPhoneById(id))
            : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: There is not any phone on the database with the informed ID. Try again with another ID!");
    }

    @PostMapping("/phone")
    public ResponseEntity<Object> saveNewPhone(@RequestBody @Valid PhoneDTO phone) {
        if (service.existsByName(phone.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: We Already Have a Phone with that NAME.");
        }
        var newPhone = new Phone(phone);
        Optional<Phone> savedPhone = service.savePhoneOnDataBase(newPhone);
        return savedPhone.isPresent() 
            ? ResponseEntity.status(HttpStatus.CREATED).body(savedPhone.get()) 
            : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: You have informed a valid new phone, but we are having Unexpected Errors!");
    }

    @DeleteMapping("/phone/{id}")
    public ResponseEntity<Object> deletePhone(@PathVariable("id") Integer id) {
        Optional<Phone> deletedPhone = service.delete(id);
        return deletedPhone.isPresent()
            ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedPhone.get())
            : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                "Error, Cannot Delete:\n\nThere is not any phone on the database with the informed ID. Try again with another ID!");
    }

    @PutMapping("/phone/{id}")
    public ResponseEntity<Object> updatePhone(@RequestBody @Valid PhoneDTO phone, @PathVariable("id") Integer id) {
        Optional<Phone> phoneToBeUpdated = service.findPhoneById(id);
        if (phoneToBeUpdated.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: We Dont have that phone yet. You should just save it!");
        }
        var updatedPhone = new Phone();
        BeanUtils.copyProperties(phone, updatedPhone);
        updatedPhone.setId(phoneToBeUpdated.get().getId());
        Optional<Phone> savedPhone = service.savePhoneOnDataBase(updatedPhone);
        return savedPhone.isPresent() 
            ? ResponseEntity.status(HttpStatus.CREATED).body(savedPhone.get()) 
            : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: You have updated an Existing phone correctly, but we are having Unexpected Errors!");
    }
    
}
