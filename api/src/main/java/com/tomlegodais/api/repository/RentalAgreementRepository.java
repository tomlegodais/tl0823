package com.tomlegodais.api.repository;

import com.tomlegodais.api.model.RentalAgreementModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalAgreementRepository extends JpaRepository<RentalAgreementModel, Long> {
}
