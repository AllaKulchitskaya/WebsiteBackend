--liquibase formatted sql

--changeset alla:1
CREATE TABLE ads
(
    ads_id     SERIAL NOT NULL,
    price      int  NOT NULL,
    title      VARCHAR(60) NOT NULL,
    description text NOT NULL,
    user_id  INTEGER NOT NULL,
    ads_image_id VARCHAR,
    PRIMARY KEY (ads_id)
);

--changeset alla:2
ALTER TABLE ads
    ADD FOREIGN KEY (user_id) REFERENCES users(user_id);
ALTER TABLE ads
    ADD FOREIGN KEY (ads_image_id) REFERENCES ads_image(ads_image_id);