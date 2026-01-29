package com.example.Assignment.DTO;

import com.example.Assignment.Enum.TokenStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateTokenResponse {
    private Integer tokenId;
    private Integer tokenNumber;
    private Integer slotId;
    private TokenStatus status;
}
