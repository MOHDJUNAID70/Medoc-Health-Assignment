package com.example.Assignment.Controller;

import com.example.Assignment.Model.OPDSlot;
import com.example.Assignment.Service.OPDSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("medoc_health")
public class OPDSlotController {

    @Autowired
    private OPDSlotService slotService;

    @PostMapping("/addSlot")
    public ResponseEntity<String> setSlot(@RequestBody OPDSlot slot) {
        slotService.setSlot(slot);
        return new ResponseEntity<>("Slot is set", HttpStatus.OK);
    }
}
