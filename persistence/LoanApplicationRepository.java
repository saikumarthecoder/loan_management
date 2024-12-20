package com.loan.mgmt.persistence;


import org.springframework.data.jpa.repository.JpaRepository;

import com.loan.mgmt.persistence.entites.LoanApplication;

import java.util.List;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    List<LoanApplication> findByCustomerId(Long customerId);
}
