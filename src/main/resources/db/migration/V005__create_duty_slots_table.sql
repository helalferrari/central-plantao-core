-- V005__create_duty_slots_table.sql

CREATE TABLE duty_slots (
    id UUID PRIMARY KEY,
    contracted_shift_id BIGINT NOT NULL,
    professional_id BIGINT,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    CONSTRAINT fk_duty_slots_contracted_shift FOREIGN KEY (contracted_shift_id) REFERENCES contracted_shifts(id) ON DELETE CASCADE,
    CONSTRAINT fk_duty_slots_professional FOREIGN KEY (professional_id) REFERENCES professionals(id) ON DELETE SET NULL
);

CREATE INDEX idx_duty_slots_shift_start ON duty_slots(contracted_shift_id, start_time);
