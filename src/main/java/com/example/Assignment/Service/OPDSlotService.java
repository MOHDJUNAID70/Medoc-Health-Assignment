package com.example.Assignment.Service;

import com.example.Assignment.Model.OPDSlot;
import com.example.Assignment.Repository.OPDSlotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OPDSlotService {

    @Autowired
    private OPDSlotRepo slotRepo;

    public void setSlot(OPDSlot slot) {
        slotRepo.save(slot);
    }
}
