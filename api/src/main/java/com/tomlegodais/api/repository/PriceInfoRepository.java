package com.tomlegodais.api.repository;

import com.tomlegodais.api.model.PriceInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceInfoRepository extends JpaRepository<PriceInfoModel, Long> {
}
