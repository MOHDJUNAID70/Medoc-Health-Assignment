package com.example.Assignment.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatientDTO {

    @NotBlank(message = "Name can't be blank")
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name can have only alphabets and space")
    private String name;

    @NotBlank(message = "You need to add your phone no.")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must have 10 digits")
    private String phone;
}
