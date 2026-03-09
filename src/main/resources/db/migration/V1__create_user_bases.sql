CREATE TABLE sectores (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE puestos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    sector_id BIGINT,
    CONSTRAINT fk_puesto_sector FOREIGN KEY (sector_id) REFERENCES sectores(id)
);

CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    domicilio VARCHAR(255),
    telefono VARCHAR(20),
    dni VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL,
    estado BOOLEAN DEFAULT TRUE
);

CREATE TABLE tokens (
    id BIGSERIAL PRIMARY KEY,
    token TEXT NOT NULL UNIQUE,
    token_type VARCHAR(20) DEFAULT 'BEARER',
    revoked BOOLEAN DEFAULT FALSE,
    expired BOOLEAN DEFAULT FALSE,
    usuario_id BIGINT,
    CONSTRAINT fk_token_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE informacion_laboral (
    id BIGSERIAL PRIMARY KEY,
    cuit VARCHAR(20) NOT NULL UNIQUE,
    cbu VARCHAR(22) NOT NULL UNIQUE,
    fecha_ingreso DATE NOT NULL,
    fecha_egreso DATE,
    categoria_fiscal VARCHAR(50),
    contrato VARCHAR(50),
    jornada VARCHAR(50),
    modalidad VARCHAR(50),
    salario_base DECIMAL(19,2) DEFAULT 0.00,
    aplica_presentismo BOOLEAN DEFAULT FALSE,
    aplica_sindicato BOOLEAN DEFAULT FALSE,
    porc_antig_anio DECIMAL(19,2) DEFAULT 0.00,
    comision DECIMAL(19,2) DEFAULT 0.00,
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE empleados (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    legajo VARCHAR(50) UNIQUE,
    disponibilidad VARCHAR(30),
    sucursal VARCHAR(100),
    puesto_id BIGINT,
    informacion_laboral_id BIGINT NOT NULL,
    objetivo_mensual DECIMAL(19,2),
    obra_social VARCHAR(100),
    usuario_id BIGINT UNIQUE NOT NULL,
    CONSTRAINT fk_empleado_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_empleado_puesto FOREIGN KEY (puesto_id) REFERENCES puestos(id),
    CONSTRAINT fk_empleado_info_laboral FOREIGN KEY (informacion_laboral_id) REFERENCES informacion_laboral(id)
);

CREATE TABLE clientes (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    tipo_cliente VARCHAR(50),
    comportamiento VARCHAR(50),
    categoria_fiscal VARCHAR(50),
    origen VARCHAR(50),
    puntuacion INTEGER DEFAULT 0,
    preferencia VARCHAR(255),
    usuario_id BIGINT UNIQUE NOT NULL,
    CONSTRAINT fk_empleado_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_cliente_usuario FOREIGN KEY (id) REFERENCES usuarios(id) ON DELETE CASCADE
);