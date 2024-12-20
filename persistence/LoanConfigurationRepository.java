package com.loan.mgmt.persistence;


import org.springframework.data.jpa.repository.JpaRepository;

import com.loan.mgmt.persistence.entites.LoanConfiguration;

import java.util.Optional;

public interface LoanConfigurationRepository extends JpaRepository<LoanConfiguration, Long> {
    Optional<LoanConfiguration> findByConfigKey(String configKey);
}
