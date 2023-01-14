package com.api.phones.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.api.phones.controllers.PhoneController;
import com.api.phones.model.Phone;
import com.api.phones.service.PhoneService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class PhonesControllerTest {

    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private PhoneService phoneService;

    @Autowired
    private MockMvc mvc;
    
    private static final String PHONES_URI = "/phones";

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void callingGetMethodOnPhonesEndpoint__ShouldBeSucessfull() throws Exception {
    
        mvc.perform(get(PHONES_URI))
           .andExpect(status().isOk());
    
    }

    @Test
    void callingPostMethodOnPhonesEndpoint__ShouldBeSucessfull() throws Exception {

        var phone = new Phone(1, "A10", "Samsung");
        var jsonBody = objectMapper.writeValueAsString(phone);

        when(phoneService.savePhoneOnDataBase(phone))
            .thenReturn(Optional.of(phone));

        mvc.perform(post(PHONES_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBody))
            .andExpect(status().isOk());


    }


}
