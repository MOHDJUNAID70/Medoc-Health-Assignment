package com.example.Assignment.Model;

import com.example.Assignment.Enum.TokenSource;
import com.example.Assignment.Enum.TokenStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GeneratedToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int tokenNumber;

    private int doctorId;
    private int patientId;
    private int slotId;

    @Enumerated(EnumType.STRING)
    private TokenSource source;

    private Integer priorityScore;

    @Enumerated(EnumType.STRING)
    private TokenStatus status;

    private LocalDateTime createdAt;
}
