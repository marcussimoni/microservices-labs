-- ==========================================================
-- Custom PostgreSQL initialization script
-- ==========================================================
CREATE USER usermanagementuser WITH PASSWORD 'usermanagementpassword';

CREATE DATABASE usermanagementdb
    WITH OWNER = usermanagementuser
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TEMPLATE = template0;

GRANT ALL PRIVILEGES ON DATABASE usermanagementdb TO usermanagementuser;

\connect usermanagementdb;

CREATE SCHEMA IF NOT EXISTS usermanagement AUTHORIZATION usermanagementuser;

GRANT ALL ON SCHEMA usermanagement TO usermanagementuser;