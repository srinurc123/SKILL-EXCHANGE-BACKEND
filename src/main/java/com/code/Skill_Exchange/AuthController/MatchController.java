package com.code.Skill_Exchange.AuthController;

import com.code.Skill_Exchange.dto.MatchRequestDTO;
import com.code.Skill_Exchange.model.MatchStatus;
import com.code.Skill_Exchange.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/match")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @PostMapping("/send")
    public ResponseEntity<MatchRequestDTO> sendMatchRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody MatchRequestDTO requestDTO) {
        MatchRequestDTO created = matchService.createRequest(userDetails.getUsername(), requestDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/received")
    public ResponseEntity<List<MatchRequestDTO>> getReceivedRequests(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<MatchRequestDTO> received = matchService.getMyReceivedRequests(userDetails.getUsername());
        return ResponseEntity.ok(received);
    }

    @GetMapping("/sent")
    public ResponseEntity<List<MatchRequestDTO>> getSentRequests(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<MatchRequestDTO> sent = matchService.getMySentRequests(userDetails.getUsername());
        return ResponseEntity.ok(sent);
    }

    @PutMapping("/respond/{requestId}")
    public ResponseEntity<MatchRequestDTO> respondToRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long requestId,
            @RequestParam MatchStatus status) {
        MatchRequestDTO updated = matchService.updateRequestStatus(requestId, status, userDetails.getUsername());
        return ResponseEntity.ok(updated);
    }
}
