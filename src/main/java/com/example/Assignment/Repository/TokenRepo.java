package com.example.Assignment.Repository;

import com.example.Assignment.Enum.TokenStatus;
import com.example.Assignment.Model.GeneratedToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepo  extends JpaRepository<GeneratedToken, Long> {

    List<GeneratedToken> findBySlotIdAndStatusOrderByTokenNumberAsc(Integer slotId, TokenStatus status);

    List<GeneratedToken> findBySlotIdAndStatusAndTokenNumberGreaterThan(int slotId, TokenStatus tokenStatus, Integer tokenNumber);

    GeneratedToken findByTokenNumber(int tokenNumber);

    boolean existsByPatientIdAndDoctorIdAndStatus(Integer patientId, Integer doctorId, TokenStatus tokenStatus);
}
