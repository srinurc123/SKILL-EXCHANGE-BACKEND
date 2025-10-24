package com.code.Skill_Exchange.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "match_requests")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MatchRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_offered_id")
    private Skill skillOffered;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_requested_id")
    private Skill skillRequested;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    private LocalDateTime requestDate;
}
