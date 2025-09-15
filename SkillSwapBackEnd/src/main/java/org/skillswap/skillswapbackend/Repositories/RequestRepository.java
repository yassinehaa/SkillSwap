package org.skillswap.skillswapbackend.Repositories;

import org.skillswap.skillswapbackend.Models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByReceiverId(Long receiverId);

    List<Request> findBySkillId(Long skillId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM requests WHERE requester_id = :userId OR receiver_id = :userId", nativeQuery = true)
    void deleteByRequesterIdOrReceiverId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM requests WHERE skill_id = :skillId", nativeQuery = true)
    void deleteBySkillId(@Param("skillId") Long skillId);

}
