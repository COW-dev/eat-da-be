-- liquibase formatted sql

-- changeset liquibase:2
CREATE TABLE notice
(
    id         bigserial   NOT NULL PRIMARY KEY,
    title      varchar(31) NOT NULL,
    content    text        NOT NULL,
    created_at timestamp   NOT NULL DEFAULT NOW(),
    updated_at timestamp   NOT NULL
);