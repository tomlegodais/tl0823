package com.tomlegodais.api.repository;

import com.tomlegodais.api.model.ToolTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToolTypeRepository extends JpaRepository<ToolTypeModel, Long> {
    boolean existsByName(String name);

    Optional<ToolTypeModel> findByName(String name);
}
