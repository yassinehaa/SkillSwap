package org.skillswap.skillswapbackend.Repositories;

import org.skillswap.skillswapbackend.Models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(value = "SELECT * FROM messages WHERE (sender_id = :senderId AND receiver_id = :receiverId) OR (sender_id = :receiverId AND receiver_id = :senderId) ORDER BY timestamp ASC", nativeQuery = true)
    List<Message> findConversation(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    @Query(value = "SELECT * FROM messages WHERE sender_id = :userId OR receiver_id = :userId ORDER BY timestamp DESC", nativeQuery = true)
    List<Message> findConversations(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM messages WHERE sender_id = :userId OR receiver_id = :userId", nativeQuery = true)
    void deleteBySenderIdOrReceiverId(@Param("userId") Long userId);
}