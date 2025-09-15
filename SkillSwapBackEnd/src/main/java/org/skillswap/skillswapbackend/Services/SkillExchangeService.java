package org.skillswap.skillswapbackend.Services;

import lombok.RequiredArgsConstructor;
import org.skillswap.skillswapbackend.Models.Request;
import org.skillswap.skillswapbackend.Models.Skill;
import org.skillswap.skillswapbackend.Models.SkillExchange;
import org.skillswap.skillswapbackend.Repositories.SkillExchangeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillExchangeService {

    private final SkillExchangeRepository skillExchangeRepository;

    public void createSkillExchange(Request request, Skill skill) {
        SkillExchange skillExchange = new SkillExchange();
        skillExchange.setRequest(request);
        skillExchange.setExchangeSkill(skill);
        skillExchangeRepository.save(skillExchange);
    }
}
