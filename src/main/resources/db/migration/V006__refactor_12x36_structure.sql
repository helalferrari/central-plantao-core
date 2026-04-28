-- V006__refactor_12x36_structure.sql

-- Adiciona a hora de início da escala nos itens do contrato
ALTER TABLE contracted_shifts ADD COLUMN start_hour TIME NOT NULL DEFAULT '07:00:00';

-- Limpa os slots antigos pois a estrutura de turnos mudou (agora são 2 turnos/dia para 12x36)
TRUNCATE TABLE duty_slots;

-- Adiciona comentário para documentação
COMMENT ON COLUMN contracted_shifts.start_hour IS 'The starting hour for the 12x36 cycle (e.g., 07:00 for a 07-19 and 19-07 coverage)';
