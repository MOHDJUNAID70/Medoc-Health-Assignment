package com.example.Assignment.Service;

import com.example.Assignment.DTO.GenerateTokenRequest;
import com.example.Assignment.DTO.GenerateTokenResponse;
import com.example.Assignment.DTO.TokenMapper;
import com.example.Assignment.Enum.TokenSource;
import com.example.Assignment.Enum.TokenStatus;
import com.example.Assignment.Model.Doctor;
import com.example.Assignment.Model.GeneratedToken;
import com.example.Assignment.Model.OPDSlot;
import com.example.Assignment.Model.Patient;
import com.example.Assignment.Repository.DoctorRepo;
import com.example.Assignment.Repository.OPDSlotRepo;
import com.example.Assignment.Repository.PatientRepo;
import com.example.Assignment.Repository.TokenRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TokenService {

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private OPDSlotRepo opdslotRepo;

    @Autowired
    private TokenRepo tokenRepo;

    @Autowired
    private OPDSlotRepo slotRepo;

    @Transactional
    public void generateToken(GenerateTokenRequest tokenRequest) {

        Doctor doctor=doctorRepo.findById(tokenRequest.getDoctorId())
                .orElseThrow(()->new RuntimeException("Doctor Not Found"));

        if(!doctor.isActive()){
            throw new RuntimeException("Doctor Not Active");
        }

        Patient patient=patientRepo.findById(tokenRequest.getPatientId())
                .orElseThrow(()->new RuntimeException("Patient Not Found"));

        OPDSlot slots=opdslotRepo.findById(tokenRequest.getSlotId())
                .orElseThrow(()->new RuntimeException("Slot Not Found"));

        if(!slots.isActive()){
            throw new RuntimeException("Slot Not Active");
        }

        if(slots.getStartTime().isAfter(slots.getEndTime())){
            throw new RuntimeException("Doctor's Slot is not available at your selected time");
        }

        if(slots.getDate().isBefore(LocalDate.now())){
            throw new RuntimeException("Slot date can't be in past");
        }

        if(!slots.getDoctorId().equals(doctor.getId())){
            throw new RuntimeException("Doctor Ids Do Not Match with Slot");
        }

        boolean isEmergency=tokenRequest.getSource()== TokenSource.EMERGENCY;

        if(!isEmergency && slots.getCurrentCount()>=slots.getMaxCapacity()){
            throw new RuntimeException("Slot Capacity Exceeded");
        }

        boolean alreadyHasToken = tokenRepo.existsByPatientIdAndDoctorIdAndStatus(
                tokenRequest.getPatientId(),
                tokenRequest.getDoctorId(),
                TokenStatus.BOOKED
        );
        if (alreadyHasToken) {
            throw new RuntimeException("Patient already has token");
        }

        int priority=tokenRequest.getSource().getPriority();

        int tokenNumber=slots.getCurrentCount()+1;

        if(isEmergency || TokenSource.PAID.equals(tokenRequest.getSource())){
            shiftExistingTokens(slots.getId(), priority);
            tokenNumber=1;
        }

        if(!isEmergency){
            slots.setCurrentCount(slots.getCurrentCount()+1);
            opdslotRepo.save(slots);
        }

        GeneratedToken token=new GeneratedToken();
        token.setDoctorId(doctor.getId());
        token.setPatientId(patient.getId());
        token.setSlotId(slots.getId());
        token.setTokenNumber(tokenNumber);
        token.setSource(tokenRequest.getSource());
        token.setStatus(TokenStatus.BOOKED);
        token.setCreatedAt(LocalDateTime.now());

        tokenRepo.save(token);
    }

    @Transactional
    public void shiftExistingTokens(Integer slotId, int incomingPriority) {
        List<GeneratedToken> existingTokens = tokenRepo.findBySlotIdAndStatusOrderByTokenNumberAsc(
                        slotId, TokenStatus.BOOKED
                );

        for(GeneratedToken token : existingTokens){
            int existingPriority = token.getSource().getPriority();
            if(existingPriority<incomingPriority){
                token.setTokenNumber(token.getTokenNumber()+1);
            }
        }
        tokenRepo.saveAll(existingTokens);
    }

    public List<GenerateTokenResponse> getAllToken() {
        return tokenRepo.findAll().stream().map(TokenMapper::ToDTO).toList();
    }


    @Transactional
    public void cancelToken(int tokenNumber) {
        GeneratedToken token=tokenRepo.findByTokenNumber(tokenNumber);
        if(token==null){
            throw new RuntimeException("Token Not Found");
        }
        if(token.getStatus()!=TokenStatus.BOOKED){
            throw new RuntimeException("Only booked tokens can be cancelled");
        }

        token.setStatus(TokenStatus.CANCELLED);
        tokenRepo.save(token);

        List<GeneratedToken> tokensToShift = tokenRepo.findBySlotIdAndStatusAndTokenNumberGreaterThan(
                        token.getSlotId(),
                        TokenStatus.BOOKED,
                        tokenNumber);

        for(GeneratedToken t : tokensToShift){
            t.setTokenNumber(t.getTokenNumber()-1);
        }
        tokenRepo.saveAll(tokensToShift);

        OPDSlot slots=slotRepo.findById(token.getSlotId())
                .orElseThrow(()->new RuntimeException("Slot Not Found"));
        slots.setCurrentCount(slots.getCurrentCount()-1);
        slotRepo.save(slots);
    }

    @Transactional
    public void marksNoShow(Integer tokenId) {
        GeneratedToken token=tokenRepo.findById(Long.valueOf(tokenId))
                .orElseThrow(()-> new RuntimeException("Token not found"));

        if(token.getStatus()!=TokenStatus.BOOKED){
            throw new RuntimeException("Only booked tokens can be marked No-show");
        }
        token.setStatus(TokenStatus.NO_SHOW);
        tokenRepo.save(token);
    }
}
