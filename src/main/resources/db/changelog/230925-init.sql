-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE Category (
    id          bigint      NOT NULL,
    name        varchar(31) NOT NULL,
    created_at  timestamp   NOT NULL,
    updated_at  timestamp   NOT NULL
);

ALTER TABLE Category ADD CONSTRAINT PK_CATEGORY PRIMARY KEY (id);
