package com.synex.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synex.service.LeaveService;

import jakarta.persistence.EntityNotFoundException;

@RestController
public class LeaveController {
	
    @Autowired
    private LeaveService leaveService;
    
    @PostMapping("/apply")
    public ResponseEntity<String> applyLeave(@RequestParam Long empId, @RequestParam String leaveType, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        try {
            leaveService.applyLeave(empId, leaveType, fromDate, toDate);
            return ResponseEntity.ok("Hey, your Leave is applied successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PostMapping("/approve/{leaveRequestId}")
    public ResponseEntity<String> approveLeave(@PathVariable Long leaveRequestId, @RequestParam String approvedBy) {
        try {
            leaveService.approveLeave(leaveRequestId, approvedBy);
            return ResponseEntity.ok("Leave approved");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave request not found");
        }
    }
}
