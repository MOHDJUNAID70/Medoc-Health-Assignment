package com.example.Assignment.DTO;

import com.example.Assignment.Enum.TokenSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateTokenRequest {
    private Integer doctorId;
    private Integer patientId;
    private Integer slotId;
    private TokenSource source;

}
