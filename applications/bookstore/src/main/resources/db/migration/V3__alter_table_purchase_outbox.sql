ALTER TABLE order_outbox
    ADD COLUMN idempotency_key UUID;