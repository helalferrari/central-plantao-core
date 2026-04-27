-- V001__create_contracts_and_contracted_shifts.sql

CREATE TABLE contracts (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE contracted_shifts (
    id BIGSERIAL PRIMARY KEY,
    sector_description VARCHAR(255) NOT NULL,
    slot_quantity INTEGER NOT NULL,
    workload VARCHAR(255) NOT NULL,
    schedule_type VARCHAR(50) NOT NULL,
    professional_type VARCHAR(50) NOT NULL,
    contract_id BIGINT NOT NULL,
    CONSTRAINT fk_contracted_shifts_contract FOREIGN KEY (contract_id) REFERENCES contracts(id) ON DELETE CASCADE
);

CREATE INDEX idx_contracted_shifts_contract ON contracted_shifts(contract_id);
