package com.loan.mgmt.persistence.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "credit_score")
    private int creditScore;

    @Column(name = "total_debt")
    private double totalDebt;

    @Column(name = "yearly_income")
    private double yearlyIncome;

    @Column(name = "account_created")
    private LocalDate accountCreated;

    @Column(name = "employment_status")
    private String employmentStatus;

    @Column(name = "loan_status")
    private String loanStatus;

    @Column(name = "min_credit_score")
    private int minCreditScore;

    @Column(name = "max_outstanding_debt")
    private double maxOutstandingDebt;

    @Column(name = "max_idr")
    private double maxIdr;

    @Column(name = "min_account_age_years")
    private int minAccountAgeYears;

    @JsonIgnore // Prevents circular reference
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoanApplication> loanApplications;

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public int getCreditScore() { return creditScore; }
    public void setCreditScore(int creditScore) { this.creditScore = creditScore; }

    public double getTotalDebt() { return totalDebt; }
    public void setTotalDebt(double totalDebt) { this.totalDebt = totalDebt; }

    public double getYearlyIncome() { return yearlyIncome; }
    public void setYearlyIncome(double yearlyIncome) { this.yearlyIncome = yearlyIncome; }

    public LocalDate getAccountCreated() { return accountCreated; }
    public void setAccountCreated(LocalDate accountCreated) { this.accountCreated = accountCreated; }

    public String getEmploymentStatus() { return employmentStatus; }
    public void setEmploymentStatus(String employmentStatus) { this.employmentStatus = employmentStatus; }

    public String getLoanStatus() { return loanStatus; }
    public void setLoanStatus(String loanStatus) { this.loanStatus = loanStatus; }

    public int getMinCreditScore() { return minCreditScore; }
    public void setMinCreditScore(int minCreditScore) { this.minCreditScore = minCreditScore; }

    public double getMaxOutstandingDebt() { return maxOutstandingDebt; }
    public void setMaxOutstandingDebt(double maxOutstandingDebt) { this.maxOutstandingDebt = maxOutstandingDebt; }

    public double getMaxIdr() { return maxIdr; }
    public void setMaxIdr(double maxIdr) { this.maxIdr = maxIdr; }

    public int getMinAccountAgeYears() { return minAccountAgeYears; }
    public void setMinAccountAgeYears(int minAccountAgeYears) { this.minAccountAgeYears = minAccountAgeYears; }

    public List<LoanApplication> getLoanApplications() { return loanApplications; }
    public void setLoanApplications(List<LoanApplication> loanApplications) { this.loanApplications = loanApplications; }
}

