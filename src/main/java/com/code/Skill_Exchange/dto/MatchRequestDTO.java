package com.code.Skill_Exchange.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MatchRequestDTO {
    private Long id;
    private Long skillOfferedId;
    private Long skillRequestedId;
    private String receiverUsername; // Username of the other user
    private String status;           // Pending, Accepted, Rejected, etc
}
