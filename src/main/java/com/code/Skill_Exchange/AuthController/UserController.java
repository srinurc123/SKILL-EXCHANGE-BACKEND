package com.code.Skill_Exchange.AuthController;

import com.code.Skill_Exchange.dto.UserDTO;
import com.code.Skill_Exchange.model.User;
import com.code.Skill_Exchange.repository.UserRepository;
import com.code.Skill_Exchange.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                        .orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userService.convertToDTO(user));
    }

    @PutMapping("/me")
    public ResponseEntity<UserDTO> updateCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UserDTO updatedUser) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                        .orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        UserDTO result = userService.updateUser(user.getId(), updatedUser).orElse(null);
        if (result == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(result);
    }
}
