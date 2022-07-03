package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class RegistrationDTO {
    @NotNull
    private String name;
    private String surname;
    private String email;
    @Email(regexp = "[0-9]{12}", message = "Phone is wrong")
    private String phone;
    private String password;

}
