package com.example.Assignment.Controller;

import com.example.Assignment.DTO.DoctorDTO;
import com.example.Assignment.Service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("medoc_health")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @PostMapping("/addDoctor")
    public ResponseEntity<String> addInfo(@Valid @RequestBody DoctorDTO doctorDTO) {
        doctorService.addDoctor(doctorDTO);
        return new ResponseEntity<>("Doctor added successfully", HttpStatus.OK);
    }
}
