package com.code.Skill_Exchange.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SkillDTO {
    private Long id;
    @NotBlank
    private String name;
    private String level;
    private String description;
}
