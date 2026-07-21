CREATE TABLE events (
                        id              UUID         PRIMARY KEY,
                        tenant_id       UUID         NOT NULL REFERENCES tenants (id) ON DELETE CASCADE,
                        event_type      VARCHAR(100) NOT NULL,
                        payload         JSONB        NOT NULL,
                        idempotency_key VARCHAR(200),
                        received_at     TIMESTAMPTZ  NOT NULL
);

CREATE UNIQUE INDEX uq_events_tenant_idempotency
    ON events (tenant_id, idempotency_key)
    WHERE idempotency_key IS NOT NULL;