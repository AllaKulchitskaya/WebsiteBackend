--liquibase formatted sql

--changeset alla:1
CREATE TABLE users
(
    user_id         SERIAL NOT NULL,
    first_name varchar(60) NOT NULL,
    last_name  varchar(60) NOT NULL,
    email      varchar(60) NOT NULL,
    phone      varchar(60) NOT NULL,
    password   varchar(60) NOT NULL,
    role       varchar(60) NOT NULL,
    user_image_id varchar,
    PRIMARY KEY (user_id)
);

--changeset alla:2
ALTER TABLE users
    ADD FOREIGN KEY (user_image_id) REFERENCES user_image(user_image_id);

--changeset alla:3
ALTER TABLE ads
    ADD FOREIGN KEY (user_id) REFERENCES users(user_id);
ALTER TABLE ads
    ADD FOREIGN KEY (ads_image_id) REFERENCES ads_image(ads_image_id);

--changeset alla:4
ALTER TABLE comment
    ADD FOREIGN KEY (user_id) REFERENCES users(user_id);
ALTER TABLE comment
    ADD FOREIGN KEY (ads_id) REFERENCES ads(ads_id);