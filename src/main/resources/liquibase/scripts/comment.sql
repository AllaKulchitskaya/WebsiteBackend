--liquibase formatted sql

--changeset alla:1
CREATE TABLE comment
(
    comment_id SERIAL NOT NULL,
    created_at timestamp with time zone,
    text       text NOT NULL,
    user_id INTEGER NOT NULL,
    ads_id INTEGER NOT NULL,
    PRIMARY KEY (comment_id)
);
