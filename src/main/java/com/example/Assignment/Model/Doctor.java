package com.example.Assignment.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String department;

    private boolean active;

}
