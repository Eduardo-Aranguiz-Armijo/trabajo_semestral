CREATE TABLE products (

                          id BIGINT PRIMARY KEY AUTO_INCREMENT,

                          name VARCHAR(255) NOT NULL,

                          description VARCHAR(500),

                          price DOUBLE PRECISION NOT NULL,

                          category_id BIGINT NOT NULL,

                          active BOOLEAN NOT NULL DEFAULT TRUE
);