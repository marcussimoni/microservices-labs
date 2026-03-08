-- Create schema if it does not exist
CREATE SCHEMA IF NOT EXISTS shipping;

-- Create table if it does not exist
CREATE TABLE IF NOT EXISTS shipping.tb_shipping (
    id BIGSERIAL PRIMARY KEY,
    state VARCHAR(255),
    city VARCHAR(255),
    book VARCHAR(255),
    sent_at TIMESTAMP,
    customer_id VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS shipping.shipping_confirmed_outbox (
    id BIGSERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    book VARCHAR(100),
    status VARCHAR(100),
    customer_id VARCHAR(100)
);

-- used by debezium for replication
CREATE PUBLICATION dbz_publication FOR TABLE shipping.shipping_confirmed_outbox;