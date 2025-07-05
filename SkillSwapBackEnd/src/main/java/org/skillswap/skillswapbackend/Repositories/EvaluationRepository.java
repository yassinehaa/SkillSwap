package org.skillswap.skillswapbackend.Repositories;

import org.skillswap.skillswapbackend.Models.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByRatedUserId(Long ratedUserId);
}
