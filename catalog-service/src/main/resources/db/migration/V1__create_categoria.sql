CREATE TABLE categoria(

                           id BIGINT PRIMARY KEY AUTO_INCREMENT,

                           nombre VARCHAR(100) NOT NULL,

                           descripcion VARCHAR(255),

                           parent_id BIGINT,

                           active BOOLEAN NOT NULL DEFAULT TRUE

);