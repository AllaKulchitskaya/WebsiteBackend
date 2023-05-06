--liquibase formatted sql

--changeset alla:1
CREATE TABLE comment
(
    comment_id SERIAL NOT NULL,
    created_at timestamp,
    text       text NOT NULL,
    user_id INTEGER NOT NULL,
    PRIMARY KEY (comment_id)
);

--changeset alla:2
ALTER TABLE comment
    ADD FOREIGN KEY (user_id) REFERENCES users(user_id);

--changeset alla:3
ALTER TABLE comment
    ADD COLUMN ads_id INTEGER NOT NULL;
ALTER TABLE comment
    ADD FOREIGN KEY (ads_id) REFERENCES ads(ads_id);
