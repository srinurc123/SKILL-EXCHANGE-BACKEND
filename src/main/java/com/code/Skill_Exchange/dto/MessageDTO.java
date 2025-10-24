package com.code.Skill_Exchange.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MessageDTO {
    private Long id;
    private String senderUsername;
    private String receiverUsername;
    @NotBlank
    private String content;
    private LocalDateTime timestamp;
}
