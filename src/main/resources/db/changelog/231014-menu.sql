-- liquibase formatted sql

-- changeset liquibase:5
CREATE TABLE menu (
    id                  bigserial       NOT NULL PRIMARY KEY ,
    name                varchar(31)     NOT NULL,
    price               integer         NOT NULL,
    image_address       varchar(255)    NULL,
    created_at          timestamp       NOT NULL DEFAULT NOW(),
    updated_at          timestamp       NOT NULL,
    store_id            bigint          NOT NULL REFERENCES store
);
