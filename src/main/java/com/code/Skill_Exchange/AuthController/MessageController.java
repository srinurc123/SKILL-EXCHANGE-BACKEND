package com.code.Skill_Exchange.AuthController;

import com.code.Skill_Exchange.dto.MessageDTO;
import com.code.Skill_Exchange.service.MessageService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<MessageDTO> sendMessage(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody MessageDTO messageDTO) {
        MessageDTO saved = messageService.sendMessage(userDetails.getUsername(), messageDTO);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/conversation/{withUsername}")
    public ResponseEntity<List<MessageDTO>> getConversation(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String withUsername) {
        List<MessageDTO> messages = messageService.getConversation(userDetails.getUsername(), withUsername);
        return ResponseEntity.ok(messages);
    }
}
