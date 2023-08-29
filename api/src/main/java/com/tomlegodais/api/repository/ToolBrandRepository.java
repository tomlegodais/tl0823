package com.tomlegodais.api.repository;

import com.tomlegodais.api.model.ToolBrandModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToolBrandRepository extends JpaRepository<ToolBrandModel, Long> {
    boolean existsByName(String name);

    Optional<ToolBrandModel> findByName(String name);
}
