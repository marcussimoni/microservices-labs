ALTER TABLE shipping_confirmed_outbox
    ADD COLUMN idempotency_key UUID;