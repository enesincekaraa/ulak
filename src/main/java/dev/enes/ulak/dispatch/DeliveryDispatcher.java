package dev.enes.ulak.dispatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DeliveryDispatcher {
    private static final Logger log = LoggerFactory.getLogger(DeliveryDispatcher.class);
    private final WebhookSender webhookSender;

    public DeliveryDispatcher(WebhookSender webhookSender) {
        this.webhookSender = webhookSender;
    }
    @Async("deliveryExecutor")
    public void dispatch(String targetUrl,String payload){
        try {
            webhookSender.send(targetUrl,payload);
        }catch (Exception e){
            log.warn("Teslimat başarısız: url={}, hata={}", targetUrl, e.getMessage());
        }
    }
}
