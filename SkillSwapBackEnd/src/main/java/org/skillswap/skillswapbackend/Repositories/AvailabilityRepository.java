package org.skillswap.skillswapbackend.Repositories;

import org.skillswap.skillswapbackend.Models.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
}
