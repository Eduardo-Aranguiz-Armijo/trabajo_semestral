CREATE TABLE cart (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         client_id BIGINT NOT NULL,
                         status VARCHAR(50) NOT NULL
);

CREATE TABLE cart_item (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              cart_id BIGINT NOT NULL,
                              product_id BIGINT NOT NULL,
                              stock INT NOT NULL,

                              CONSTRAINT fk_cart_item_cart
                                  FOREIGN KEY (cart_id)
                                      REFERENCES cart(id)
                                      ON DELETE CASCADE
);