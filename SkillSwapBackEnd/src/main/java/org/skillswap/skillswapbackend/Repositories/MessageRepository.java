package org.skillswap.skillswapbackend.Repositories;

import org.skillswap.skillswapbackend.Models.Message;
import org.skillswap.skillswapbackend.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndReceiverOrReceiverAndSenderOrderByTimestamp(
            User sender, User receiver, User receiver2, User sender2);
}
