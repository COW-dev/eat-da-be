-- liquibase formatted sql

-- changeset liquibase:6
CREATE TABLE banner
(
    id            bigserial    NOT NULL PRIMARY KEY,
    link          varchar(255) NOT NULL,
    image_address varchar(255) NOT NULL,
    expired_at    timestamp,
    created_at    timestamp    NOT NULL DEFAULT NOW(),
    updated_at    timestamp    NOT NULL
);