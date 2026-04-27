-- V002__alter_workload_column_type.sql

ALTER TABLE contracted_shifts 
    ALTER COLUMN workload TYPE VARCHAR(50);
