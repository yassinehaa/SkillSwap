
package org.skillswap.skillswapbackend.repository;

import org.skillswap.skillswapbackend.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByReceiverId(Long receiverId);
}
