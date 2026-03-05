-- 1. Eliminamos las secuencias que Hibernate creó para las tablas hijas
DROP SEQUENCE IF EXISTS empleados_id_seq CASCADE;
DROP SEQUENCE IF EXISTS clientes_id_seq CASCADE;

-- 2. Quitamos el valor por defecto (nextval) de las columnas ID
-- para que hereden el ID de la tabla usuarios
ALTER TABLE empleados ALTER COLUMN id DROP DEFAULT;
ALTER TABLE clientes ALTER COLUMN id DROP DEFAULT;

-- 3. Nos aseguramos de que el ID de las hijas sea una FK hacia usuarios
-- (Si ya existía, esto refuerza la integridad del JOINED)
ALTER TABLE empleados
    ADD CONSTRAINT fk_empleados_usuarios
    FOREIGN KEY (id) REFERENCES usuarios(id) ON DELETE CASCADE;

ALTER TABLE clientes
    ADD CONSTRAINT fk_clientes_usuarios
    FOREIGN KEY (id) REFERENCES usuarios(id) ON DELETE CASCADE;