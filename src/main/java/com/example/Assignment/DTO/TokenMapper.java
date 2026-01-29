package com.example.Assignment.DTO;

import com.example.Assignment.Model.GeneratedToken;
import org.springframework.stereotype.Component;


@Component
public class TokenMapper {
    public static GenerateTokenResponse ToDTO(GeneratedToken token) {
        GenerateTokenResponse dto = new GenerateTokenResponse();
        dto.setTokenId(token.getId());
        dto.setTokenNumber(token.getTokenNumber());
        dto.setSlotId(token.getSlotId());
        dto.setStatus(token.getStatus());
        return dto;
    }
}
