package com.api.phones.dto;

import javax.validation.constraints.NotBlank;

import com.api.phones.model.Phone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String brand;


    public PhoneDTO(Phone phone) {
        this.name = phone.getName();
        this.brand = phone.getBrand();
    }

}
