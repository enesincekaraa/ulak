package dev.enes.ulak.dispatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class WebhookSender {
    private static final Logger log = LoggerFactory.getLogger(WebhookSender.class);

    private final RestClient webhookRestClient;

    WebhookSender(RestClient webhookRestClient) {
        this.webhookRestClient = webhookRestClient;
    }

    void send(String targetUrl,String payload) {
        log.info("Teslimat başlıyor: url={}", targetUrl);
        webhookRestClient.post()
                .uri(targetUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload)
                .retrieve()
                .toBodilessEntity();
        log.info("Teslimat başarılı: url={}", targetUrl);

    }
}
