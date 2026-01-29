package com.example.Assignment.Controller;

import com.example.Assignment.DTO.GenerateTokenRequest;
import com.example.Assignment.DTO.GenerateTokenResponse;
import com.example.Assignment.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medoc_health")
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @GetMapping("/getToken")
    public List<GenerateTokenResponse> getToken() {
        return tokenService.getAllToken();
    }

    @PostMapping("/setToken")
    public ResponseEntity<String> generateToken(@RequestBody GenerateTokenRequest tokenRequest) {
        tokenService.generateToken(tokenRequest);
        return new ResponseEntity<>("Your token has been generated", HttpStatus.OK);
    }

    @GetMapping("/cancelToken")
    public ResponseEntity<String> cancelToken(@RequestParam("tokenNumber") int tokenNumber) {
        tokenService.cancelToken(tokenNumber);
        return new ResponseEntity<>("Your token has been cancelled", HttpStatus.OK);
    }

    @GetMapping("/no_show")
    public ResponseEntity<String> noShow(@RequestParam("tokenId") Integer tokenId) {
        tokenService.marksNoShow(tokenId);
        return new ResponseEntity<>("Your token has been marked for show", HttpStatus.OK);
    }
}
