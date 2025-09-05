package org.skillswap.skillswapbackend.Repositories;

import org.skillswap.skillswapbackend.Models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByUserId(Long userId);
    List<Skill> findByType(Skill.SkillType type);
    List<Skill> findByUserIdAndType(Long userId, Skill.SkillType type);
    void deleteByUserId(Long userId);
    List<Skill>findByNameAndType(String name, Skill.SkillType type);
}
