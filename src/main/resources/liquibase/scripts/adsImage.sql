--liquibase formatted sql

--changeset alla:1
CREATE TABLE ads_image
(
    ads_image_id VARCHAR NOT NULL,
    image bytea,
    PRIMARY KEY (ads_image_id)
);