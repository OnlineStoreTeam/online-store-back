package com.store.dto.userDTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserRegisterDTO {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Please, check your email")
    private String email;
  
    private String password;
  
    private String confirmPassword;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;


    private String password;

    private String confirmPassword;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Override
    public String toString() {
        return "{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}