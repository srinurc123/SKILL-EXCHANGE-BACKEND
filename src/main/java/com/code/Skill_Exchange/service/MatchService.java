package com.code.Skill_Exchange.service;

import com.code.Skill_Exchange.dto.MatchRequestDTO;
import com.code.Skill_Exchange.model.*;
import com.code.Skill_Exchange.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRequestRepository matchRequestRepo;
    private final SkillRepository skillRepo;
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;

    public MatchRequestDTO createRequest(String senderUsername, MatchRequestDTO reqDTO) {
        User sender = userRepo.findByUsername(senderUsername).orElseThrow();
        User receiver = userRepo.findByUsername(reqDTO.getReceiverUsername()).orElseThrow();
        Skill skillOffered = skillRepo.findById(reqDTO.getSkillOfferedId()).orElseThrow();
        Skill skillRequested = skillRepo.findById(reqDTO.getSkillRequestedId()).orElseThrow();

        MatchRequest request = MatchRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .skillOffered(skillOffered)
                .skillRequested(skillRequested)
                .status(MatchStatus.PENDING)
                .requestDate(LocalDateTime.now())
                .build();
        request = matchRequestRepo.save(request);
        return modelMapper.map(request, MatchRequestDTO.class);
    }

    public List<MatchRequestDTO> getMyReceivedRequests(String username) {
        User receiver = userRepo.findByUsername(username).orElseThrow();
        return matchRequestRepo.findByReceiverId(receiver.getId()).stream()
                .map(r -> modelMapper.map(r, MatchRequestDTO.class))
                .collect(Collectors.toList());
    }

    public List<MatchRequestDTO> getMySentRequests(String username) {
        User sender = userRepo.findByUsername(username).orElseThrow();
        return matchRequestRepo.findBySenderId(sender.getId()).stream()
                .map(r -> modelMapper.map(r, MatchRequestDTO.class))
                .collect(Collectors.toList());
    }

    public MatchRequestDTO updateRequestStatus(Long requestId, MatchStatus status, String receiverUsername) {
        User receiver = userRepo.findByUsername(receiverUsername).orElseThrow();
        MatchRequest request = matchRequestRepo.findById(requestId).orElseThrow();
        if (!request.getReceiver().getId().equals(receiver.getId())) throw new IllegalArgumentException("Not your request");
        request.setStatus(status);
        matchRequestRepo.save(request);
        return modelMapper.map(request, MatchRequestDTO.class);
    }
}
