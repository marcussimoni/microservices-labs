-- ==========================================================
-- Custom PostgreSQL initialization script
-- ==========================================================
CREATE USER bookstoreuser WITH PASSWORD 'bookstorepassword';

CREATE DATABASE bookstoredb
    WITH OWNER = bookstoreuser
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TEMPLATE = template0;

GRANT ALL PRIVILEGES ON DATABASE bookstoredb TO bookstoreuser;

\connect bookstoredb;

CREATE SCHEMA IF NOT EXISTS bookstore AUTHORIZATION bookstoreuser;

GRANT ALL ON SCHEMA bookstore TO bookstoreuser;