-- liquibase formatted sql

-- changeset liquibase:8
CREATE TABLE curation (
    id            bigserial    NOT NULL PRIMARY KEY,
    title          varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    created_at    timestamp,
    updated_at    timestamp    NOT NULL
);

CREATE TABLE curation_store (
    id          bigserial   NOT NULL PRIMARY KEY,
    curation_id bigint      NOT NULL REFERENCES curation,
    store_id    bigint      NOT NULL REFERENCES store,
    created_at  timestamp   NOT NULL DEFAULT NOW(),
    updated_at    timestamp    NOT NULL
);
