package org.skillswap.skillswapbackend.Repositories;

import org.skillswap.skillswapbackend.Models.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM evaluations WHERE rater_id = :userId OR rated_user_id = :userId", nativeQuery = true)
    void deleteByRaterIdOrRatedUserId(@Param("userId") Long userId);

    List<Evaluation> findByRatedUserId(Long ratedUserId);
}