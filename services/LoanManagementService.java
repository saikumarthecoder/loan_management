package com.loan.mgmt.services;

import java.util.List;
import java.util.Map;

import com.loan.mgmt.persistence.entites.Customer;
import com.loan.mgmt.persistence.entites.LoanApplication;

public interface LoanManagementService {

    List<Customer> getAllCustomers();


    Map<String, Object> checkLoanEligibility(Customer customer);


    Map<String, Double> calculateMaxLoan(Customer customer);


    String applyForLoan(LoanApplication loanApplication);


    List<LoanApplication> getLoanHistory(Long customerId);
}
