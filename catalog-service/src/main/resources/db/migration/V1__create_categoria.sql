CREATE TABLE category(

                           id BIGINT PRIMARY KEY AUTO_INCREMENT,

                           name VARCHAR(100) NOT NULL,

                           description VARCHAR(255),

                           active BOOLEAN NOT NULL DEFAULT TRUE

);