package com.code.Skill_Exchange.AuthController;

import com.code.Skill_Exchange.dto.SkillDTO;
import com.code.Skill_Exchange.model.User;
import com.code.Skill_Exchange.repository.UserRepository;
import com.code.Skill_Exchange.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;
    private final UserRepository userRepository;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SkillDTO>> getSkillsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(skillService.getSkillsByUserId(userId));
    }

    @PostMapping("/me")
    public ResponseEntity<SkillDTO> addSkill(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody SkillDTO skillDTO) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                        .orElse(null);
        if (user == null) return ResponseEntity.badRequest().build();
        SkillDTO savedSkill = skillService.addSkillToUser(user.getId(), skillDTO);
        return ResponseEntity.ok(savedSkill);
    }

    @DeleteMapping("/me/{skillId}")
    public ResponseEntity<Void> deleteSkill(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long skillId) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                    .orElse(null);
        if (user == null) return ResponseEntity.badRequest().build();
        skillService.deleteSkill(skillId, user.getId());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/me")
    public ResponseEntity<List<SkillDTO>> getMySkills(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(skillService.getSkillsByUserId(user.getId()));
    }

}
