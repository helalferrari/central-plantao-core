-- V003__create_client_and_professional_tables.sql

CREATE TABLE clients (
    id BIGSERIAL PRIMARY KEY,
    corporate_name VARCHAR(255) NOT NULL,
    trade_name VARCHAR(255) NOT NULL,
    document VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE professionals (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    document VARCHAR(20) NOT NULL UNIQUE,
    registration_number VARCHAR(50) NOT NULL,
    professional_type VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Clean up existing data to allow NOT NULL constraint on mandatory relationship
-- (Since contract structure changed significantly, old test data is incompatible)
DELETE FROM contracted_shifts;
DELETE FROM contracts;

-- Refactor contracts table
ALTER TABLE contracts RENAME COLUMN name TO description;
ALTER TABLE contracts ADD COLUMN client_id BIGINT;

-- Add Foreign Key and set NOT NULL
ALTER TABLE contracts ADD CONSTRAINT fk_contracts_client FOREIGN KEY (client_id) REFERENCES clients(id);
ALTER TABLE contracts ALTER COLUMN client_id SET NOT NULL;
