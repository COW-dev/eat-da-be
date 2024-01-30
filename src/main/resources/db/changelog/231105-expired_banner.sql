-- liquibase formatted sql

-- changeset liquibase:7
CREATE TABLE expired_banner
(
    id            bigserial    NOT NULL PRIMARY KEY,
    link          varchar(255) NOT NULL,
    image_address varchar(255) NOT NULL,
    expired_at    timestamp
);
