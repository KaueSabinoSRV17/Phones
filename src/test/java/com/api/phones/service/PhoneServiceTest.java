package com.api.phones.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import com.api.phones.model.Phone;
import com.api.phones.repository.PhoneRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class PhoneServiceTest {

    @Mock
    private PhoneRepository repository;
    private PhoneService underTest;

    private AutoCloseable autoClosable;

    @BeforeEach
    void setup() {
        autoClosable = MockitoAnnotations.openMocks(this);
        underTest = new PhoneService(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoClosable.close(); 
    }

    @Test
    void canGetAllPhonesTest__ShouldBeSucessfull() {

        // Given
        var pageable = Mockito.any(Pageable.class);
        
        // When
        underTest.findAllPhones(pageable);

        // Then
        verify(repository).findAll(pageable);

    }

    @Test
    void canGetPhoneById__ShouldBeSucessfull() {

        // Given
        int id = 1;

        // When
        underTest.findPhoneById(id);

        // Then
        verify(repository).findById(id);

    }

    @Test
    void savesANewPhoneAndGetsItByItsId__ShouldBeSucessfull() {

        // Given
        var phone = new Phone(1, "A10", "Samsung");
        ArgumentCaptor<Phone> phoneExpectedToBePassed = ArgumentCaptor.forClass(Phone.class);

        // When
        underTest.savePhoneOnDataBase(phone);

        // Then
        boolean phoneAlreadyExists = verify(repository).existsByName(phone.getName());
        assertFalse(phoneAlreadyExists);

        verify(repository).save(phoneExpectedToBePassed.capture());

        Phone phonePassedToRepository = phoneExpectedToBePassed.getValue();

        assertEquals(phonePassedToRepository, phone);

    }

    @Test
    void deletesAPhoneByItsId__ShouldBeSucessfull() {

        // Given
        int id = 1;
        when(repository.existsById(id)).thenReturn(true);

        // When
        underTest.delete(id);

        // Then
        verify(repository).existsById(id);
        verify(repository).deleteById(id);

    }


}
