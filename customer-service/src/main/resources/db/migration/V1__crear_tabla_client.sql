CREATE TABLE clients (
                        id_customer BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        rut VARCHAR(50) NOT NULL UNIQUE,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        phone VARCHAR(50) NOT NULL,
                        address VARCHAR(255) NOT NULL,
                        user_id BIGINT NOT NULL UNIQUE
);