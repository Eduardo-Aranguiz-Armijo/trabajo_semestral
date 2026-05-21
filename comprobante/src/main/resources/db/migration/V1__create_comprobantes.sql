CREATE TABLE comprobantes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    payment_id BIGINT NOT NULL UNIQUE,
    order_id BIGINT NOT NULL,
    cliente_id BIGINT NOT NULL,
    numero_comprobante VARCHAR(50) NOT NULL UNIQUE,
    amount DOUBLE NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL
);
