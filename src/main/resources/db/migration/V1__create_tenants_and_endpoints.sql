create table tenants(
    id UUID PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL
);

create table endpoints(
    id UUID PRIMARY KEY ,
    tenant_id UUID NOT NULL REFERENCES tenants (id) on delete cascade,
    url         VARCHAR(2048) NOT NULL,
    description VARCHAR(500),
    active      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMPTZ  NOT NULL
);

CREATE INDEX idx_endpoints_tenant_active
    on endpoints(tenant_id,active);

CREATE TABLE endpoint_subscriptions (
                                        endpoint_id UUID        NOT NULL REFERENCES endpoints (id) ON DELETE CASCADE,
                                        event_type  VARCHAR(100) NOT NULL,
                                        PRIMARY KEY (endpoint_id, event_type)
);

CREATE INDEX idx_subscriptions_event_type
    ON endpoint_subscriptions (event_type);