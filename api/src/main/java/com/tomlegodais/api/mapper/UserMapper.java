package com.tomlegodais.api.mapper;

import com.tomlegodais.api.dto.UserRegistrationDto;
import com.tomlegodais.api.model.AppUserModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public abstract AppUserModel dboToModel(UserRegistrationDto dto);

    @AfterMapping
    protected void hashPassword(UserRegistrationDto dto, @MappingTarget AppUserModel model) {
        model.setPassword(passwordEncoder.encode(dto.getPassword()));
    }
}
