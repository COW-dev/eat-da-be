-- liquibase formatted sql

-- changeset liquibase:2
CREATE TABLE store (
    id              bigserial   NOT NULL PRIMARY KEY ,
    name            varchar(31) NOT NULL,
    display_name    varchar(31) NULL,
    created_at      timestamp   NOT NULL DEFAULT NOW(),
    updated_at      timestamp   NOT NULL
);
CREATE UNIQUE INDEX idx_store_name ON store(name);

CREATE TABLE store_category (
    id          bigserial   NOT NULL PRIMARY KEY,
    store_id    bigint      NOT NULL REFERENCES store,
    category_id bigint      NOT NULL REFERENCES category,
    created_at  timestamp   NOT NULL DEFAULT NOW()
);
