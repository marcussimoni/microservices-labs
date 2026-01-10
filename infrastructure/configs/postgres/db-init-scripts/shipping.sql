-- ==========================================================
-- Custom PostgreSQL initialization script
-- ==========================================================
CREATE USER shippinguser WITH PASSWORD 'shippingpassword';

CREATE DATABASE shippingdb
    WITH OWNER = shippinguser
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TEMPLATE = template0;

GRANT ALL PRIVILEGES ON DATABASE shippingdb TO shippinguser;

\connect shippingdb;

CREATE SCHEMA IF NOT EXISTS shipping AUTHORIZATION shippinguser;

GRANT ALL ON SCHEMA shipping TO shippinguser;