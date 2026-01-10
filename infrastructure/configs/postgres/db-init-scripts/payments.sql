-- ==========================================================
-- Custom PostgreSQL initialization script
-- ==========================================================
CREATE USER paymentsuser WITH PASSWORD 'paymentspassword';

CREATE DATABASE paymentsdb
    WITH OWNER = paymentsuser
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TEMPLATE = template0;

GRANT ALL PRIVILEGES ON DATABASE paymentsdb TO paymentsuser;

\connect paymentsdb;

CREATE SCHEMA IF NOT EXISTS payments AUTHORIZATION paymentsuser;

GRANT ALL ON SCHEMA payments TO paymentsuser;