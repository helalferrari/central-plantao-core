-- V004__add_client_id_to_professionals.sql

-- Since we are in dev, and professionals might have data, we'll clear it to apply NOT NULL
DELETE FROM professionals;

ALTER TABLE professionals ADD COLUMN client_id BIGINT NOT NULL;
ALTER TABLE professionals ADD CONSTRAINT fk_professionals_client FOREIGN KEY (client_id) REFERENCES clients(id);

CREATE INDEX idx_professionals_client ON professionals(client_id);
