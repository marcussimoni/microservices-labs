ALTER TABLE purchase_outbox
    ADD COLUMN idempotency_key UUID;