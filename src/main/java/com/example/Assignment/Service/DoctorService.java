package com.example.Assignment.Service;

import com.example.Assignment.DTO.DoctorDTO;
import com.example.Assignment.Model.Doctor;
import com.example.Assignment.Repository.DoctorRepo;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    DoctorRepo doctorRepo;

    @Transactional
    public void addDoctor(@Valid DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        doctor.setName(doctorDTO.getName());
        doctor.setDepartment(doctorDTO.getDepartment());
        doctor.setActive(doctorDTO.isActive());
        doctorRepo.save(doctor);
    }
}
