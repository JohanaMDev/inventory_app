CREATE TABLE categorias (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE productos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    marca VARCHAR(100),
    codigo_barras VARCHAR(100) NOT NULL UNIQUE,
    precio_costo DECIMAL(19,2),
    precio_venta DECIMAL(19,2) NOT NULL,
    margen_ganancia DECIMAL(19,2) DEFAULT 0.00,
    iva DECIMAL(19,2) DEFAULT 21.00,
    stock_actual INTEGER NOT NULL DEFAULT 0,
    stock_minimo INTEGER,
    activo BOOLEAN DEFAULT TRUE,
    categoria_id BIGINT,
    CONSTRAINT fk_producto_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);