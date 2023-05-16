--liquibase formatted sql

--changeset alla:1
CREATE TABLE user_image
(
    user_image_id VARCHAR NOT NULL,
    image bytea,
    PRIMARY KEY (user_image_id)
);