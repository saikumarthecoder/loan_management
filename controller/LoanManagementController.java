package com.loan.mgmt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.loan.mgmt.persistence.entites.Customer;
import com.loan.mgmt.persistence.entites.LoanApplication;
import com.loan.mgmt.services.LoanManagementService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/loan")
public class LoanManagementController {

    @Autowired
     LoanManagementService loanManagementService;

    @PostMapping("/apply")
    public String applyForLoan(@RequestBody LoanApplication loanApplication) {
        return loanManagementService.applyForLoan(loanApplication);  // Ensure this method is called correctly
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return loanManagementService.getAllCustomers();
    }

    @PostMapping("/checkEligibility")
    public Map<String, Object> checkLoanEligibility(@RequestBody Customer customer) {
        return loanManagementService.checkLoanEligibility(customer);
    }

    @PostMapping("/loanCalculator")
    public Map<String, Double> calculateMaxLoan(@RequestBody Customer customer) {
        return loanManagementService.calculateMaxLoan(customer);
    }

    @GetMapping("/history/{customerId}")
    public List<LoanApplication> getLoanHistory(@PathVariable Long customerId) {
        return loanManagementService.getLoanHistory(customerId);
    }
}
