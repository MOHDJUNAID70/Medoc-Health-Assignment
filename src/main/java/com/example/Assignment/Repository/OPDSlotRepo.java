package com.example.Assignment.Repository;

import com.example.Assignment.Model.OPDSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OPDSlotRepo extends JpaRepository<OPDSlot, Integer> {
}
