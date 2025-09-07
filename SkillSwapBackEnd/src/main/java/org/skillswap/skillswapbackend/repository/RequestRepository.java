
package org.skillswap.skillswapbackend.repository;

import org.skillswap.skillswapbackend.models.Request;
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

    @Modifying
    @Transactional
    @Query("DELETE FROM Request r WHERE r.requester.id = :userId OR r.receiver.id = :userId")
    void deleteByRequesterIdOrReceiverId(@Param("userId") Long userId);
}
