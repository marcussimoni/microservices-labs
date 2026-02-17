-- 1. Create the tracking table if it doesn't exist
CREATE TABLE IF NOT EXISTS processed_events (
    idempotency_key UUID NOT NULL,
    handler_name VARCHAR(100),
    processed_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_processed_events PRIMARY KEY (idempotency_key, handler_name));

ALTER TABLE payment_confirmed_outbox
    ADD COLUMN IF NOT EXISTS idempotency_key UUID;

ALTER TABLE payment_declined_outbox
    ADD COLUMN IF NOT EXISTS idempotency_key UUID;