package org.skillswap.skillswapbackend.Repositories;

import org.skillswap.skillswapbackend.Models.User;
import org.skillswap.skillswapbackend.dto.SkillDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    
}
