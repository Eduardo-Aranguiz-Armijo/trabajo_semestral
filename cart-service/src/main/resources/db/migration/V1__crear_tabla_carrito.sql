CREATE TABLE carrito (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         cliente_id BIGINT NOT NULL,
                         estado VARCHAR(50) NOT NULL
);

CREATE TABLE carrito_item (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              cart_id BIGINT NOT NULL,
                              product_id BIGINT NOT NULL,
                              cantidad INT NOT NULL,

                              CONSTRAINT fk_cart_item_cart
                                  FOREIGN KEY (cart_id)
                                      REFERENCES carrito(id)
                                      ON DELETE CASCADE
);