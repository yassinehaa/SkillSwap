package org.skillswap.skillswapbackend.Services;

import org.modelmapper.ModelMapper;
import org.skillswap.skillswapbackend.Models.Skill;
import org.skillswap.skillswapbackend.Models.User;
import org.skillswap.skillswapbackend.Repositories.UserRepository;
import org.skillswap.skillswapbackend.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SkillService skillService;

    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Encode password
        user.setPremium(false); // Default to non-Premium
        user = userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setProposedSkills(skillService.getSkillsByUserIdAndType(id, Skill.SkillType.OFFERED));
        userDTO.setSearchedSkills(skillService.getSkillsByUserIdAndType(id, Skill.SkillType.SEARCHED));
        return userDTO;
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setProposedSkills(skillService.getSkillsByUserIdAndType(user.getId(), Skill.SkillType.OFFERED));
        userDTO.setSearchedSkills(skillService.getSkillsByUserIdAndType(user.getId(), Skill.SkillType.SEARCHED));
        return userDTO;
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (userDTO.getId() != null && !userDTO.getId().equals(id)) {
            throw new RuntimeException("User ID in request body does not match path ID");
        }
        // Update basic user information
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPremium(userDTO.isPremium());

        // Update skills
        skillService.deleteSkillsByUserId(id);
        if (userDTO.getProposedSkills() != null && !userDTO.getProposedSkills().isEmpty()) {
            skillService.addSkills(id, userDTO.getProposedSkills());
        }
        if (userDTO.getSearchedSkills() != null && !userDTO.getSearchedSkills().isEmpty()) {
            skillService.addSkills(id, userDTO.getSearchedSkills());
        }

        user = userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO togglePremium(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPremium(!user.isPremium());
        user = userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    public void updateUserPremiumStatus(String username, boolean isPremium) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPremium(isPremium);
        userRepository.save(user);
    }

    public List<UserDTO> getPremiumUsers() {
        return userRepository.findByIsPremiumTrueOrderById()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
}

