package com.synex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synex.domain.LeaveApproval;

@Repository
public interface LeaveApprovalRepository extends JpaRepository<LeaveApproval, Long> {
}