-- 1. Eliminamos las columnas de permisos de la tabla puesto
ALTER TABLE puestos
DROP COLUMN IF EXISTS puede_vender,
DROP COLUMN IF EXISTS puede_gestionar_inventario,
DROP COLUMN IF EXISTS puede_gestionar_empleados,
DROP COLUMN IF EXISTS puede_ver_reportes_sensibles,
DROP COLUMN IF EXISTS puede_gestionar_clientes,
DROP COLUMN IF EXISTS puede_realizar_mantenimiento,
DROP COLUMN IF EXISTS puede_configurar_sistema;

-- 2. Aseguramos que la tabla usuarios tenga la columna rol
-- Nota: Si ya existe, esto solo asegura que el tipo de dato sea consistente
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name='usuarios' AND column_name='rol') THEN
        ALTER TABLE usuarios ADD COLUMN rol VARCHAR(50);
    END IF;
END $$;

-- 3. (Opcional) Si quieres limpiar usuarios antiguos o resetear el SUPER_ADMIN para pruebas
UPDATE usuarios SET rol = 'SUPER_ADMIN' WHERE email = 'test@mendoza.com';