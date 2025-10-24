package com.code.Skill_Exchange.service;

import com.code.Skill_Exchange.dto.UserDTO;
import com.code.Skill_Exchange.model.User;
import com.code.Skill_Exchange.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDTO.class));
    }

    public Optional<UserDTO> updateUser(Long id, UserDTO updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setBio(updatedUser.getBio());
            user.setLocation(updatedUser.getLocation());
            userRepository.save(user);
            return modelMapper.map(user, UserDTO.class);
        });
    }
 // Only the new method added for demonstration
    public UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

}
