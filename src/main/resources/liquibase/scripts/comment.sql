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

--changeset alla:2
ALTER TABLE comment
    ADD FOREIGN KEY (user_id) REFERENCES users(user_id);
ALTER TABLE comment
    ADD FOREIGN KEY (ads_id) REFERENCES ads(ads_id);
