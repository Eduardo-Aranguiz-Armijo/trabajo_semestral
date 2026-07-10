CREATE TABLE orders (

                        id BIGINT PRIMARY KEY AUTO_INCREMENT,

                        client_id BIGINT NOT NULL,

                        cart_id BIGINT NOT NULL,

                        total DOUBLE NOT NULL,

                        status VARCHAR(50) NOT NULL,

                        created_at TIMESTAMP NOT NULL
);

CREATE TABLE order_items (

                            id BIGINT PRIMARY KEY AUTO_INCREMENT,

                            order_id BIGINT NOT NULL,

                            product_id BIGINT NOT NULL,

                            quantity INT NOT NULL,

                            price DOUBLE NOT NULL,



                            CONSTRAINT fk_order_item_order
                                FOREIGN KEY (order_id)
                                    REFERENCES orders(id)
);