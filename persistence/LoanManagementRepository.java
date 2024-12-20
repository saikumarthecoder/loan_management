package com.loan.mgmt.persistence;


import org.springframework.data.jpa.repository.JpaRepository;

import com.loan.mgmt.persistence.entites.Customer;



public interface LoanManagementRepository extends JpaRepository<Customer, Long> {
}