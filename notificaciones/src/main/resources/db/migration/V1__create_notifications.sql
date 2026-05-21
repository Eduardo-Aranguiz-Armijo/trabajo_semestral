CREATE TABLE IF NOT EXISTS notifications (
                                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                             cliente_id BIGINT NOT NULL,
                                             order_id BIGINT,
                                             payment_id BIGINT,
                                             type VARCHAR(50) NOT NULL,
                                            channel VARCHAR(20) NOT NULL,
                                            subject VARCHAR(255) NOT NULL,
                                            message TEXT NOT NULL,
                                            status VARCHAR(20) NOT NULL,
                                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                            sent_at TIMESTAMP NULL DEFAULT NULL
                                            );