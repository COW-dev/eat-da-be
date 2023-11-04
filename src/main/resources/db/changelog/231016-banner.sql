-- liquibase formatted sql

-- changeset liquibase:5
CREATE TABLE banner
(
    id            bigserial    NOT NULL PRIMARY KEY,
    link          varchar(255) NOT NULL,
    image_address varchar(255) NOT NULL,
    expired_at    timestamp    NOT NULL,
    created_at    timestamp    NOT NULL DEFAULT NOW(),
    updated_at    timestamp    NOT NULL
);