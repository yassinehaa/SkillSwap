package org.skillswap.skillswapbackend.Repositories;

import org.skillswap.skillswapbackend.Models.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}
