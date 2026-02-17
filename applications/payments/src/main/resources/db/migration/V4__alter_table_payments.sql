ALTER TABLE payments.payments
ADD COLUMN transaction_id VARCHAR(100),
ADD COLUMN authorization_code VARCHAR(100),
ADD COLUMN receipt_url VARCHAR(100),
ADD COLUMN customer_id VARCHAR(100);