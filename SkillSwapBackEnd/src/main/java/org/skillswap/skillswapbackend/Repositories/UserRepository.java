package org.skillswap.skillswapbackend.Repositories;

import org.skillswap.skillswapbackend.Models.User;
import org.skillswap.skillswapbackend.dto.UserDTO2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT users.first_name as name, COUNT(requests.requester_id) AS skillCount FROM users LEFT JOIN requests ON users.id = requests.requester_id GROUP BY users.id, users.first_name", nativeQuery = true)
    List<Object[]> countSkills();
    
}
