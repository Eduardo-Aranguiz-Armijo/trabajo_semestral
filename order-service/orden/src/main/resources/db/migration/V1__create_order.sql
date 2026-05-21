CREATE TABLE orders (

                        id BIGINT PRIMARY KEY AUTO_INCREMENT,

                        cliente_id BIGINT NOT NULL,

                        cart_id BIGINT NOT NULL,

                        total DOUBLE NOT NULL,

                        estado VARCHAR(50) NOT NULL,

                        created_at TIMESTAMP NOT NULL
);