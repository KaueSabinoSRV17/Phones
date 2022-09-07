package com.api.phones.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.api.phones.service.PhoneService;
import com.api.phones.model.Phone;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class PhoneController {
    
    @Autowired
    private PhoneService service;

    @GetMapping("/")
    public String home() {
        return "<h1>Home do site - Live Reload Funcionando!</h1>";
    }
    @GetMapping("/phones")
    public List<Phone> listOfPhones() {
        return service.findAllPhones();
    }

    @GetMapping("/phone/{id}")
    public Phone phoneById(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
        return service.findPhoneById(id).get();
    }

    @GetMapping(value="/phone")
    public void saveNewPhone(@RequestBody ResponseEntity<Phone> phone) {
        // Phone phone = new Phone();
        // service.savePhoneOnDataBase(phone);
    }
    
}
