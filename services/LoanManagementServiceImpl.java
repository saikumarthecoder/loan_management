package com.loan.mgmt.services;




import org.springframework.stereotype.Service;

import com.loan.mgmt.persistence.LoanApplicationRepository;
import com.loan.mgmt.persistence.LoanConfigurationRepository;
import com.loan.mgmt.persistence.LoanManagementRepository;
import com.loan.mgmt.persistence.entites.Customer;
import com.loan.mgmt.persistence.entites.LoanApplication;

import java.time.LocalDate;
import java.util.*;

@Service
public class LoanManagementServiceImpl implements LoanManagementService {

    private final LoanManagementRepository customerRepository;
    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanConfigurationRepository configRepository;

    public LoanManagementServiceImpl(
            LoanManagementRepository customerRepository,
            LoanApplicationRepository loanApplicationRepository,
            LoanConfigurationRepository configRepository) {
        this.customerRepository = customerRepository;
        this.loanApplicationRepository = loanApplicationRepository;
        this.configRepository = configRepository;
    }

    /**
     * Retrieve all customers.
     */
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Helper method to fetch a configuration value as Double.
     */
    private double getConfigValueAsDouble(String key) {
        try {
            return Double.parseDouble(configRepository.findByConfigKey(key)
                    .orElseThrow(() -> new RuntimeException("Config not found for key: " + key))
                    .getConfigValue());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid configuration value for key: " + key);
        }
    }

    /**
     * Helper method to fetch a configuration value as Integer.
     */
    private int getConfigValueAsInt(String key) {
        return (int) Math.round(getConfigValueAsDouble(key));
    }

    /**
     * Check loan eligibility for a customer based on configured criteria.
     */
    @Override
    public Map<String, Object> checkLoanEligibility(Customer customer) {
        Map<String, Object> result = new HashMap<>();
        boolean eligible = true;

        int minCreditScore = getConfigValueAsInt("MIN_CREDIT_SCORE");
        double maxIdr = getConfigValueAsDouble("MAX_IDR");
        double maxOutstandingDebt = getConfigValueAsDouble("MAX_OUTSTANDING_DEBT");
        int minAccountAge = getConfigValueAsInt("MIN_ACCOUNT_AGE");

        // Validate credit score
        if (customer.getCreditScore() < minCreditScore) {
            result.put("Credit Score", "Too low");
            eligible = false;
        }

        // Validate outstanding debt
        if (customer.getTotalDebt() > maxOutstandingDebt) {
            result.put("Outstanding Loan Limit", "Exceeded");
            eligible = false;
        }

        // Validate Income-to-Debt Ratio (IDR)
        double idr = customer.getTotalDebt() / customer.getYearlyIncome();
        if (idr > maxIdr) {
            result.put("Income-to-Debt Ratio", "Too high");
            eligible = false;
        }

        // Validate account age
        if (customer.getAccountCreated().isAfter(LocalDate.now().minusYears(minAccountAge))) {
            result.put("Account Age", "Less than " + minAccountAge + " years");
            eligible = false;
        }

        // Validate employment status
        if ("Unemployed".equalsIgnoreCase(customer.getEmploymentStatus())) {
            result.put("Employment Status", "Unemployed not eligible");
            eligible = false;
        }

        // Final eligibility result
        result.put("Eligibility", eligible ? "Approved" : "Rejected");
        return result;
    }

    /**
     * Calculate the maximum loan amount a customer is eligible for.
     */
    @Override
    public Map<String, Double> calculateMaxLoan(Customer customer) {
        double maxIdr = getConfigValueAsDouble("MAX_IDR");
        double maxLoan = (customer.getYearlyIncome() * maxIdr) - customer.getTotalDebt();
        return Map.of("Maximum Loan Amount", Math.max(maxLoan, 0));
    }

    /**
     * Apply for a loan. Save the loan application with default status and date.
     */
    @Override
    public String applyForLoan(LoanApplication loanApplication) {
        // Validate input
        if (loanApplication.getCustomer() == null || loanApplication.getLoanAmount() <= 0) {
            throw new IllegalArgumentException("Invalid loan application details.");
        }

        // Set the default status and application date
        if (loanApplication.getStatus() == null || loanApplication.getStatus().isEmpty()) {
            loanApplication.setStatus("PENDING");
        }
        loanApplication.setDateApplied(LocalDate.now());

        loanApplicationRepository.save(loanApplication);
        return "Loan Application Submitted Successfully!";
    }

    /**
     * Retrieve the loan application history for a specific customer.
     */
    @Override
    public List<LoanApplication> getLoanHistory(Long customerId) {
        return loanApplicationRepository.findByCustomerId(customerId);
    }
}
