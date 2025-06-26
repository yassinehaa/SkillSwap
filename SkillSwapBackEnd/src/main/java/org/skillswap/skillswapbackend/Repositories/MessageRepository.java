package org.skillswap.skillswapbackend.Repositories;

import org.skillswap.skillswapbackend.Models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
