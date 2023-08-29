package com.tomlegodais.api.controller;

import com.tomlegodais.api.dto.UserRegistrationDto;
import com.tomlegodais.api.mapper.UserMapper;
import com.tomlegodais.api.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserController(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDto dto) {
        if (repository.existsByUsername(dto.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        var appUser = repository.save(mapper.dboToModel(dto));
        return ResponseEntity.ok(appUser);
    }
}
