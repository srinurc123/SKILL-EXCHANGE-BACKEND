package com.code.Skill_Exchange.config;

import com.code.Skill_Exchange.model.Role;
import com.code.Skill_Exchange.repository.RoleRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final RoleRepository roleRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initRoles() {
        roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_USER").build()));
    }
}
