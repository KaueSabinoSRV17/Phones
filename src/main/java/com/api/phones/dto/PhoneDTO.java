package com.api.phones.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String brand;
}
