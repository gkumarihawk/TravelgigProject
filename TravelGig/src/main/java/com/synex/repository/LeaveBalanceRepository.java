package com.synex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synex.domain.LeaveBalance;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {
	
    LeaveBalance findByEmpIdAndLeaveTypeAndLeaveYear(Long empId, String leaveType, int leaveYear);
}
