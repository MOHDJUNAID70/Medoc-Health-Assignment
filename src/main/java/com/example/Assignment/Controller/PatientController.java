package com.example.Assignment.Controller;


import com.example.Assignment.DTO.PatientDTO;
import com.example.Assignment.Model.Patient;
import com.example.Assignment.Service.PatientService;
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
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/addPatient")
    public ResponseEntity<String> addInfo(@Valid @RequestBody PatientDTO patientDTO) {
        patientService.addPatient(patientDTO);
        return new ResponseEntity<>("Patient added successfully", HttpStatus.OK);
    }
}
