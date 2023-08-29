package com.tomlegodais.api.repository;

import com.tomlegodais.api.model.ToolModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToolRepository extends JpaRepository<ToolModel, Long> {

    Optional<ToolModel> findByCode(String code);

    boolean existsByCode(String code);
}
