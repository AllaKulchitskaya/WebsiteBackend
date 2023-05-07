--liquibase formatted sql

--changeset alla:1
CREATE TABLE ads
(
    ads_id       SERIAL      NOT NULL,
    price        int         NOT NULL,
    title        varchar(60) NOT NULL,
    description  text        NOT NULL,
    user_id      INTEGER     NOT NULL,
    comment_id   INTEGER     NOT NULL,
    ads_image_id INTEGER     NOT NULL,
    PRIMARY KEY (ads_id)
);