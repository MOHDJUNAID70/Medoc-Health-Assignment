package com.example.Assignment.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DoctorDTO {

    @NotBlank(message = "Name can't be blank")
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name can only have Alphabets and Space")
    private String name;

    @NotBlank(message = "Department should have any name")
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Department name can have only alphabets and space")
    private String department;

    private boolean active;
}
