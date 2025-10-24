package com.code.Skill_Exchange.service;

import com.code.Skill_Exchange.dto.ChatMessage;
import com.code.Skill_Exchange.dto.MessageDTO;
import com.code.Skill_Exchange.model.Message;
import com.code.Skill_Exchange.model.User;
import com.code.Skill_Exchange.repository.MessageRepository;
import com.code.Skill_Exchange.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public MessageDTO sendMessage(String senderUsername, MessageDTO messageDTO) {
        User sender = userRepository.findByUsername(senderUsername).orElseThrow();
        User receiver = userRepository.findByUsername(messageDTO.getReceiverUsername()).orElseThrow();

        Message message = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .content(messageDTO.getContent())
                .timestamp(LocalDateTime.now())
                .build();

        Message savedMsg = messageRepository.save(message);
        return modelMapper.map(savedMsg, MessageDTO.class);
    }

    public List<MessageDTO> getConversation(String user1, String user2) {
        User sender = userRepository.findByUsername(user1).orElseThrow();
        User receiver = userRepository.findByUsername(user2).orElseThrow();

        List<Message> messagesSent = messageRepository.findBySenderIdAndReceiverIdOrderByTimestampAsc(sender.getId(), receiver.getId());
        List<Message> messagesReceived = messageRepository.findBySenderIdAndReceiverIdOrderByTimestampAsc(receiver.getId(), sender.getId());

        messagesSent.addAll(messagesReceived);
        messagesSent.sort((m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()));

        return messagesSent.stream()
                .map(msg -> modelMapper.map(msg, MessageDTO.class))
                .collect(Collectors.toList());
    }
    public Message saveChatMessage(ChatMessage chatMessage) {
        User sender = userRepository.findByUsername(chatMessage.getSender()).orElseThrow();
        User receiver = userRepository.findByUsername(chatMessage.getReceiver()).orElseThrow();

        Message message = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .content(chatMessage.getContent())
                .timestamp(Instant.ofEpochMilli(chatMessage.getTimestamp()).atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();

        return messageRepository.save(message);
    }

}
