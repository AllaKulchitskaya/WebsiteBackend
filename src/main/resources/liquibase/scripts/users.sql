--liquibase formatted sql

--changeset alla:1
CREATE TABLE users
(
    user_id         SERIAL NOT NULL,
    first_name varchar(60) NOT NULL,
    last_name  varchar(60) NOT NULL,
    email      varchar(60) NOT NULL,
    phone      varchar(60) NOT NULL,
    username   varchar(60) NOT NULL,
    password   varchar(60) NOT NULL,
    role       varchar(60) NOT NULL,
    user_image_id INTEGER NOT NULL,
    PRIMARY KEY (user_id)
);

--ads
--changeset alla:2
ALTER TABLE ads
    ADD FOREIGN KEY (user_id) REFERENCES users(user_id);
ALTER TABLE ads
    ADD FOREIGN KEY (comment_id) REFERENCES comment(comment_id);
ALTER TABLE ads
    ADD FOREIGN KEY (ads_image_id) REFERENCES ads_image(ads_image_id);
--ads2
--changeset alla:3
ALTER TABLE ads
DROP CONSTRAINT ads_comment_id_fkey;
ALTER TABLE ads
DROP COLUMN comment_id;

--comment
--changeset alla:4
ALTER TABLE comment
    ADD FOREIGN KEY (user_id) REFERENCES users(user_id);
--comment2
--changeset alla:5
ALTER TABLE comment
    ADD COLUMN ads_id INTEGER NOT NULL;
ALTER TABLE comment
    ADD FOREIGN KEY (ads_id) REFERENCES ads(ads_id);
--users
--changeset alla:6
ALTER TABLE users
    ADD FOREIGN KEY (user_image_id) REFERENCES user_image(user_image_id);
--users2
--changeset alla:7
ALTER TABLE users
DROP COLUMN username;