package com.code.Skill_Exchange.AuthController;

import com.code.Skill_Exchange.dto.ChatMessage;
import com.code.Skill_Exchange.service.MessageService;
import com.code.Skill_Exchange.model.Message;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.time.Instant;

@Controller
@RequiredArgsConstructor
public class WebSocketChatController {

    private final MessageService messageService;
    private final ModelMapper modelMapper;

    @MessageMapping("/chat.send")
    @SendToUser("/queue/messages")
    public ChatMessage sendChatMessage(ChatMessage chatMessage, Authentication authentication) {
        // Validate user authentication
        if (authentication == null) {
            throw new IllegalStateException("User is not authenticated");
        }

        // Set sender and timestamp
        chatMessage.setSender(authentication.getName());
        chatMessage.setTimestamp(Instant.now().toEpochMilli());

        // Save message to DB
        Message savedMsg = messageService.saveChatMessage(chatMessage);

        // Map back to DTO
        return modelMapper.map(savedMsg, ChatMessage.class);
    }
}
