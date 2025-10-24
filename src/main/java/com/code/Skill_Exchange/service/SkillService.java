package com.code.Skill_Exchange.service;

import com.code.Skill_Exchange.dto.SkillDTO;
import com.code.Skill_Exchange.model.Skill;
import com.code.Skill_Exchange.model.User;
import com.code.Skill_Exchange.repository.SkillRepository;
import com.code.Skill_Exchange.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<SkillDTO> getSkillsByUserId(Long userId) {
        return skillRepository.findByUserId(userId).stream()
                .map(skill -> modelMapper.map(skill, SkillDTO.class))
                .collect(Collectors.toList());
    }

    public SkillDTO addSkillToUser(Long userId, SkillDTO skillDTO) {
        User user = userRepository.findById(userId).orElseThrow();
        Skill skill = modelMapper.map(skillDTO, Skill.class);
        skill.setUser(user);
        Skill saved = skillRepository.save(skill);
        return modelMapper.map(saved, SkillDTO.class);
    }

    public void deleteSkill(Long skillId, Long userId) {
        skillRepository.findById(skillId).ifPresent(skill -> {
            if (skill.getUser().getId().equals(userId)) {
                skillRepository.delete(skill);
            }
        });
    }
}
