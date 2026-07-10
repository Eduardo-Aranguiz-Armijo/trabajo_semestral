CREATE TABLE payment_methods (

                                 id BIGINT PRIMARY KEY AUTO_INCREMENT,

                                 client_id BIGINT NOT NULL,

                                 card_holder VARCHAR(100) NOT NULL,

                                 card_number VARCHAR(16)
                                     NOT NULL UNIQUE,

                                 expiration_date VARCHAR(5)
                                     NOT NULL,

                                 cvv VARCHAR(4)
                                     NOT NULL,

                                 created_at TIMESTAMP
);