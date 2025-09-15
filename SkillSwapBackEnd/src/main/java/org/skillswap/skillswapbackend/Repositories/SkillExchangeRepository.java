package org.skillswap.skillswapbackend.Repositories;

import org.skillswap.skillswapbackend.Models.SkillExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SkillExchangeRepository extends JpaRepository<SkillExchange, Long> {
    @Transactional
    void deleteByExchangeSkillId(Long skillId);

    @Transactional
    void deleteByRequestId(Long requestId);
}
