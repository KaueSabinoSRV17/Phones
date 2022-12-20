package com.api.phones.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import com.api.phones.exceptions.DomainException;
import com.api.phones.model.Phone;
import com.api.phones.repository.PhoneRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class PhoneServiceTest {

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

    // <=== HAPPY PATH TESTS ===>

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

    @Test
    void canCheckIfAPhoneExistsByName__ShouldBeSucessfull() {

        // Given
        String name = "A10";
        // When
        underTest.existsByName(name);
        // Then
        verify(repository).existsByName(name);

    }

    @Test
    void canCheckIfPhoneExistsById__ShouldBeSucessfull() {

        // Given
        int id = 1;
        // When
        underTest.existsById(id);
        // Then
        verify(repository).existsById(id);

    }

    // <=== UNHAPPY PATH TESTS ===>

    @Test
    void cannotSaveARepeatedPhone__ShouldThrowDomainException() {

        // Given
        var phone = new Phone(1, "A10", "Samsung");
        when(repository.existsByName(phone.getName()))
            .thenReturn(true);

        doThrow(DomainException.class)
            .when(repository)
            .save(phone);

        // When
        assertThrows(DomainException.class, () -> underTest.savePhoneOnDataBase(phone));

        // Then
        verify(repository, never()).save(phone);
    }

    @Test
    void cannotDeleteAPhoneThatDoesNotExists__ShouldThrowDomainException() {

        // Given
        int id = 1;
        // When
        assertThrows(DomainException.class, 
            () -> underTest.delete(id));
        // Then
        verify(repository).existsById(id);

        doThrow(DomainException.class)
            .when(repository)
            .deleteById(id);

        verify(repository, never()).deleteById(id);

    }

    @Test
    void cannotUpdateAPhoneThatDoesNotExists__ShouldThrowDomainException() {

        // Given
        var phone = new Phone(1, "A10", "Samsung");
        when(repository.existsById(phone.getId()))
            .thenReturn(false);

        // When
        assertThrows(DomainException.class, 
            () -> underTest.updatePhoneOnDataBase(phone, phone.getId()));
        // Then
        verify(repository, never()).save(phone);

    }


}
