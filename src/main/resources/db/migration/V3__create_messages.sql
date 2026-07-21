CREATE TABLE messages (
                          id            UUID         PRIMARY KEY,
                          event_id      UUID         NOT NULL REFERENCES events (id) ON DELETE CASCADE,
                          endpoint_id   UUID         NOT NULL REFERENCES endpoints (id) ON DELETE CASCADE,
                          target_url    VARCHAR(2048) NOT NULL,
                          payload       JSONB        NOT NULL,
                          status        VARCHAR(20)  NOT NULL,
                          attempt_count INT          NOT NULL DEFAULT 0,
                          created_at    TIMESTAMPTZ  NOT NULL,
                          updated_at    TIMESTAMPTZ  NOT NULL
);

CREATE INDEX idx_messages_status_created
    ON messages (status, created_at);