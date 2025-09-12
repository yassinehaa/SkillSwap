package org.skillswap.skillswapbackend.Repositories;

import org.skillswap.skillswapbackend.Models.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM availabilities WHERE user_id = :userId", nativeQuery = true)
    void deleteByUserId(@Param("userId") Long userId);
}