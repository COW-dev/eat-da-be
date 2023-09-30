-- liquibase formatted sql

-- changeset liquibase:2
CREATE EXTENSION postgis;

CREATE TABLE store (
    id                  bigserial               NOT NULL PRIMARY KEY ,
    name                varchar(31)             NOT NULL,
    display_name        varchar(31)             NULL,
    address             varchar(63)             NOT NULL,
    contact             varchar(31)             NULL,
    image_address       varchar(255)            NULL,
    location            geometry(Point, 4326)   NULL,
    created_at          timestamp               NOT NULL DEFAULT NOW(),
    updated_at          timestamp               NOT NULL
);
CREATE UNIQUE INDEX idx_store_name ON store(name);

CREATE TABLE store_category (
    id          bigserial   NOT NULL PRIMARY KEY,
    store_id    bigint      NOT NULL REFERENCES store,
    category_id bigint      NOT NULL REFERENCES category,
    created_at  timestamp   NOT NULL DEFAULT NOW()
);
