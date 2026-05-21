CREATE TABLE inventory (

                           id BIGINT PRIMARY KEY AUTO_INCREMENT,

                           product_id BIGINT NOT NULL UNIQUE,

                           stock INT NOT NULL,

                           updated_at TIMESTAMP NOT NULL
);