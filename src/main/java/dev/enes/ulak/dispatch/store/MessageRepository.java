package dev.enes.ulak.dispatch.store;

import dev.enes.ulak.dispatch.domain.Message;
import dev.enes.ulak.dispatch.domain.MessageStatus;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findByStatusOrderByCreatedAtAsc(MessageStatus status, Limit limit);
}
