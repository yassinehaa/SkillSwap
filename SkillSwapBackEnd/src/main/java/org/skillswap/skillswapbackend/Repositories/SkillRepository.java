package org.skillswap.skillswapbackend.Repositories;

import org.skillswap.skillswapbackend.Models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
