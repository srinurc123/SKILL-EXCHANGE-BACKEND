package com.code.Skill_Exchange.config;

import com.code.Skill_Exchange.model.Role;
import com.code.Skill_Exchange.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleInitializer {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            roleRepository.save(Role.builder().name("ROLE_USER").build());
            System.out.println("ROLE_USER initialized");
        } else {
            System.out.println("ROLE_USER already initialized");
        }
    }

}
