-- liquibase formatted sql

-- changeset liquibase:5
CREATE TABLE banner
(
    id            bigserial    NOT NULL PRIMARY KEY,
    display_order smallint     NOT NULL,
    link          varchar(255) NOT NULL,
    image_address varchar(255) NOT NULL,
    created_at    timestamp    NOT NULL DEFAULT NOW(),
    updated_at    timestamp    NOT NULL
);