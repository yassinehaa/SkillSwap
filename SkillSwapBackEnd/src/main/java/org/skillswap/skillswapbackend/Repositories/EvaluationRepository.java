package org.skillswap.skillswapbackend.Repositories;

import org.skillswap.skillswapbackend.Models.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Evaluation e WHERE e.rater.id = :userId OR e.ratedUser.id = :userId")
    void deleteByRaterIdOrRatedUserId(@Param("userId") Long userId);
}