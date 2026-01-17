-- Payment tables
CREATE TABLE IF NOT EXISTS payments.payment_confirmed_outbox (
    id BIGSERIAL PRIMARY KEY,
    purchase_id INTEGER NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    book VARCHAR(100),
    status VARCHAR(100),
    public_identifier VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS payments.payment_declined_outbox (
     id BIGSERIAL PRIMARY KEY,
     purchase_id INTEGER NOT NULL,
     amount DECIMAL(10, 2) NOT NULL,
     book VARCHAR(100),
     status VARCHAR(100),
     public_identifier VARCHAR(100)
 );

-- used by debezium for replication
CREATE PUBLICATION dbz_publication FOR TABLE payments.payment_confirmed_outbox, payments.payment_declined_outbox;
