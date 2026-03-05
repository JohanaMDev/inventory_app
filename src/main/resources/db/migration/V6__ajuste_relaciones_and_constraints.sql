-- 1. Intentamos eliminar cualquier PK existente en clientes de forma genérica
DO $$
BEGIN
    EXECUTE (
        SELECT 'ALTER TABLE clientes DROP CONSTRAINT ' || constraint_name
        FROM information_schema.table_constraints
        WHERE table_name = 'clientes' AND constraint_type = 'PRIMARY KEY'
    );
EXCEPTION WHEN OTHERS THEN
    RAISE NOTICE 'No se pudo eliminar la PK o no existía';
END $$;

-- 2. Ahora sí, definimos el ID heredado como la nueva PK
ALTER TABLE clientes
    ADD CONSTRAINT pk_clientes_usuario PRIMARY KEY (id);

-- 3. Refuerzo de la relación Empleado -> Información Laboral (1:1)
-- Primero eliminamos si ya existe para evitar error de duplicado
ALTER TABLE empleados DROP CONSTRAINT IF EXISTS uq_informacion_laboral;
ALTER TABLE empleados ADD CONSTRAINT uq_informacion_laboral UNIQUE (informacion_laboral_id);

-- 4. Indexación para Performance
CREATE INDEX IF NOT EXISTS idx_venta_empleado ON venta(empleado_id);
CREATE INDEX IF NOT EXISTS idx_venta_cliente ON venta(cliente_id);
CREATE INDEX IF NOT EXISTS idx_empleado_puesto ON empleados(puesto_id);