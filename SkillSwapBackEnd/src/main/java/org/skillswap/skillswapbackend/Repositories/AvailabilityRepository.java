package org.skillswap.skillswapbackend.Repositories;

import org.skillswap.skillswapbackend.Models.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByUserId(Long userId);
    List<Availability> findByUserIdAndStartTimeBetween(Long userId, LocalDateTime start, LocalDateTime end);
}