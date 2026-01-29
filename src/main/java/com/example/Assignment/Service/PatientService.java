package com.example.Assignment.Service;


import com.example.Assignment.DTO.PatientDTO;
import com.example.Assignment.Model.Patient;
import com.example.Assignment.Repository.PatientRepo;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    PatientRepo patientRepo;

    @Transactional
    public void addPatient(@Valid PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setName(patientDTO.getName());
        patient.setPhone(patientDTO.getPhone());
        patientRepo.save(patient);
    }
}
