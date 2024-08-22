package com.synex.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synex.domain.LeaveRequest;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
	
    List<LeaveRequest> findByEmpIdAndFromDateLessThanEqualAndToDateGreaterThanEqual(Long empId, LocalDate toDate, LocalDate fromDate);
}
