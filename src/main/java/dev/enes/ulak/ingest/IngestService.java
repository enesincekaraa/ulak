package dev.enes.ulak.ingest;

import dev.enes.ulak.dispatch.domain.Message;
import dev.enes.ulak.dispatch.store.MessageRepository;
import dev.enes.ulak.endpoint.EndpointApi;
import dev.enes.ulak.endpoint.EndpointView;
import dev.enes.ulak.ingest.domain.Event;
import dev.enes.ulak.ingest.store.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import dev.enes.ulak.dispatch.WebhookSender;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
class IngestService implements IngestApi {
    private static final Logger log = LoggerFactory.getLogger(IngestService.class);

    private final EventRepository eventRepository;
    private final EndpointApi endpointApi;
    private final MessageRepository messageRepository;

    IngestService(EventRepository eventRepository, EndpointApi endpointApi, MessageRepository messageRepository) {
        this.eventRepository = eventRepository;
        this.endpointApi = endpointApi;
        this.messageRepository = messageRepository;
    }

    @Override
    @Transactional
    public UUID ingest(UUID tenantId, String eventType, String payload, String idempotencyKey) {
        Event event = new Event(tenantId, eventType, payload, idempotencyKey);
        eventRepository.save(event);
        log.info("Event kaydedildi: eventId={}, type={}", event.getId(), eventType);

        List<EndpointView> subscribers = endpointApi.findActiveSubscribers(tenantId, eventType);
        for (EndpointView subscriber : subscribers) {
            Message message = new Message(event.getId(),subscriber.id(),subscriber.url(),payload);
            messageRepository.save(message);
        }
        log.info("Teslimat kaydı oluşturuldu: eventId={}, mesajSayısı={}", event.getId(), subscribers.size());
        return event.getId();

    }
}
