-- liquibase formatted sql

-- changeset liquibase:9
ALTER TABLE curation
ADD COLUMN image_address TEXT NOT NULL DEFAULT 'image';

ALTER TABLE curation_store
ALTER COLUMN updated_at SET DEFAULT NOW();
