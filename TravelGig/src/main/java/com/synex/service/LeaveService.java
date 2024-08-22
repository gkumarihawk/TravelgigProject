package com.synex.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synex.domain.LeaveApproval;
import com.synex.domain.LeaveBalance;
import com.synex.domain.LeaveRequest;
import com.synex.repository.LeaveApprovalRepository;
import com.synex.repository.LeaveBalanceRepository;
import com.synex.repository.LeaveRequestRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LeaveService {

	@Autowired
    private LeaveBalanceRepository leaveBalanceRepository;
    
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;
    
    @Autowired
    private LeaveApprovalRepository leaveApprovalRepository;
    
    public void applyLeave(Long empId, String leaveType, LocalDate fromDate, LocalDate toDate) {
        // Is the leave sufficient or insufficient (Checking it here)
        LeaveBalance leaveBalance = leaveBalanceRepository.findByEmpIdAndLeaveTypeAndLeaveYear(empId, leaveType, Year.now().getValue());
        if (leaveBalance == null || leaveBalance.getBalance() <= 0) {
            throw new IllegalStateException("Sufficient leave balance not available");
            
        }
        
        //make sure the applied leave does not clash with the already applied leave
        List<LeaveRequest> conflictingRequests = leaveRequestRepository.findByEmpIdAndFromDateLessThanEqualAndToDateGreaterThanEqual(empId, toDate, fromDate);
        if (!conflictingRequests.isEmpty()) {
            throw new IllegalStateException("Leave already applied for given range");
            
        }
        
        //This part is working
        //Just remove what has been applied
        leaveBalance.setBalance(leaveBalance.getBalance() - 1);
        leaveBalanceRepository.save(leaveBalance);
        
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmpId(empId);
        leaveRequest.setLeaveType(leaveType);
        leaveRequest.setFromDate(fromDate);
        leaveRequest.setToDate(toDate);
        leaveRequest.setStatus("Pending");
        
        leaveRequestRepository.save(leaveRequest);
    }
    
    public void approveLeave(Long leaveRequestId, String approvedBy) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId).orElseThrow(() -> new EntityNotFoundException("Leave request not found"));
        leaveRequest.setStatus("Approved");
        leaveRequestRepository.save(leaveRequest);
        
        LeaveApproval leaveApproval = new LeaveApproval();
        leaveApproval.setLeaveRequest(leaveRequest);
        leaveApproval.setStatus("Approved");
        leaveApproval.setApprovedBy(approvedBy);
        leaveApproval.setApprovedOn(LocalDateTime.now());
        leaveApprovalRepository.save(leaveApproval);
    }
}
