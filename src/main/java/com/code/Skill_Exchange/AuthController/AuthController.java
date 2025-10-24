package com.code.Skill_Exchange.AuthController;

import com.code.Skill_Exchange.dto.AuthRequest;
import com.code.Skill_Exchange.dto.AuthResponse;
import com.code.Skill_Exchange.dto.RegisterRequest;
import com.code.Skill_Exchange.model.Role;
import com.code.Skill_Exchange.model.User;
import com.code.Skill_Exchange.repository.RoleRepository;
import com.code.Skill_Exchange.repository.UserRepository;
import com.code.Skill_Exchange.security.JwtTokenProvider;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        try {
            if (userRepository.existsByUsername(signUpRequest.getUsername())) {
                return ResponseEntity.badRequest().body("Username is already taken.");
            }
            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity.badRequest().body("Email already in use.");
            }
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("ROLE_USER not found!"));
            User user = User.builder()
                    .username(signUpRequest.getUsername())
                    .email(signUpRequest.getEmail())
                    .password(passwordEncoder.encode(signUpRequest.getPassword()))
                    .roles(Collections.singleton(userRole))
                    .build();
            userRepository.save(user);
            String jwt = jwtTokenProvider.generateToken(user.getUsername());
            return ResponseEntity.ok(new AuthResponse(jwt));
        } catch (Exception e) {
            e.printStackTrace(); // This will show the real reason in your server console!
            return ResponseEntity.badRequest().body("Registration error: " + e.getMessage());
        }
    }



    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody AuthRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        String jwt = jwtTokenProvider.generateToken(authentication.getName());
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
