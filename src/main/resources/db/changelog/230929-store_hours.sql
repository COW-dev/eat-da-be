-- liquibase formatted sql

-- changeset liquibase:3
CREATE TABLE store_hours (
    id                  bigserial   NOT NULL PRIMARY KEY ,
    day_of_week         smallint    NOT NULL,
    open_at             smallint    NOT NULL,
    close_at            smallint    NOT NULL,
    created_at          timestamp   NOT NULL DEFAULT NOW(),
    updated_at          timestamp   NOT NULL
);
