-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE category (
    id          bigserial                   NOT NULL,
    name        varchar(31)                 NOT NULL,
    created_at  timestamp with time zone    NOT NULL,
    updated_at  timestamp with time zone    NOT NULL
);

ALTER TABLE category ADD CONSTRAINT PK_CATEGORY PRIMARY KEY (id);
CREATE UNIQUE INDEX idx_category_name ON category(name);
