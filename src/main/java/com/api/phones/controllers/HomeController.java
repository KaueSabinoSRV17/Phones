package com.api.phones.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.utils.StringUtils;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class HomeController {
    
    @GetMapping
    public String home() {
        StringBuilder lista = new StringBuilder("<ul>");
        String title = StringUtils.htmlElement("h1", "Welcome to the Phone API!");
        lista.append(StringUtils.htmlElement("li", "GET /phones: Returns all the phones"));
        lista.append(StringUtils.htmlElement("li", "GET /phones/{id}: Returns a phone by ID"));
        lista.append(StringUtils.htmlElement("li", "POST + Valid Body with Phone Name and Phone Brand /phones/{id}: Saves a new Phone, if it does not already exists"));
        lista.append(StringUtils.htmlElement("li", "DELETE /phones/{id}: Deletes a phone by ID, if it does exists"));
        lista.append(StringUtils.htmlElement("li", "PUT /phone/{id}: Updates a phone by ID, if it does exists"));
        lista.append("</ul>");
        return title + lista;
    }
}
