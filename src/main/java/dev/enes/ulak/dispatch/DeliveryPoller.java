package dev.enes.ulak.dispatch;

import dev.enes.ulak.dispatch.domain.Message;
import dev.enes.ulak.dispatch.domain.MessageStatus;
import dev.enes.ulak.dispatch.store.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Limit;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
class DeliveryPoller {
    private static final Logger log= LoggerFactory.getLogger(DeliveryPoller.class);
    private final MessageRepository messageRepository;
    private final WebhookSender webhookSender;

    DeliveryPoller(MessageRepository messageRepository, WebhookSender webhookSender) {
        this.messageRepository = messageRepository;
        this.webhookSender = webhookSender;
    }


    @Scheduled(fixedDelay = 3000)
    @Transactional
    void pollAndDeliver() {
        List<Message> pending = messageRepository
                .findByStatusOrderByCreatedAtAsc(MessageStatus.PENDING, Limit.of(10));

        if (pending.isEmpty()) {
            return;
        }
        log.info("İşlenecek mesaj sayısı: {}", pending.size());

        for (Message message : pending) {
            try {
                webhookSender.send(message.getTargetUrl(), message.getPayload());
                message.markDelivered();
            }catch (Exception e){
                message.markFailed();
                log.warn("Mesaj teslim edilemedi: messageId={}, hata={}",
                        message.getId(), e.getMessage());
            }
        }
    }
}
