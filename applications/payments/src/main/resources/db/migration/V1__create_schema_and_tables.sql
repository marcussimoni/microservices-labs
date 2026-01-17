-- 1. Create the schema if it doesn't already exist
CREATE SCHEMA IF NOT EXISTS payments;

-- 2. Create the table within the 'payments' schema
CREATE TABLE IF NOT EXISTS payments.payments (
    id BIGSERIAL PRIMARY KEY,
    amount NUMERIC(15, 2) NOT NULL,
    payment_at TIMESTAMP NOT NULL,
    purchase_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);