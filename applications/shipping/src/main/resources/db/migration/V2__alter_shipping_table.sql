ALTER TABLE shipping.tb_shipping
    ADD COLUMN courier_payload JSONB;

ALTER TABLE shipping.shipping_confirmed_outbox
    ADD COLUMN courier_payload JSONB;