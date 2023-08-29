package com.tomlegodais.api.listener;

import com.tomlegodais.api.model.AppUserModel;
import com.tomlegodais.api.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ApplicationReadyListener {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ApplicationReadyListener(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReady() {
        if (userRepository.existsByUsername("user")) {
            return;
        }

        userRepository.save(new AppUserModel("user", passwordEncoder.encode("password")));
    }
}
