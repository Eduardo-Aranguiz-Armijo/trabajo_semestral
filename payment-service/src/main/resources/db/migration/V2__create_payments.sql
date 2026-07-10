CREATE TABLE payments (

                          id BIGINT PRIMARY KEY AUTO_INCREMENT,

                          order_id BIGINT NOT NULL,

                          cliente_id BIGINT NOT NULL,

                          payment_method_id BIGINT NOT NULL,

                          amount DOUBLE NOT NULL,

                          status VARCHAR(20)
                              NOT NULL,

                          paid_at TIMESTAMP
);