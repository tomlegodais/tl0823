package com.tomlegodais.api.repository;

import com.tomlegodais.api.model.AppUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUserModel, Long> {
    Optional<AppUserModel> findByUsername(String username);

    boolean existsByUsername(String username);
}
