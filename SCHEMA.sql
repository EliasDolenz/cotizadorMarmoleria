-- =====================================================
-- ESQUEMA BASE: COTIZADOR PARAMÉTRICO PARA MARMOLERÍA
-- =====================================================
-- Base de Datos: cotizador_marmoleria
-- Motor: MySQL 8.4.0
-- Nota: Este es el esquema conceptual. Flyway generará las migraciones.

-- =====================================================
-- 1. CATÁLOGOS (Datos Maestros)
-- =====================================================

-- TERMINACIÓN (Acabados de Materiales)
CREATE TABLE terminacion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(500),
    precio_adicional_m2 DECIMAL(19, 2) DEFAULT 0.00,
    activo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- BORDE (Tipos de Bordes: Regrueso, Inglete, Pulido, etc.)
CREATE TABLE borde (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(500),
    costo_por_metro_lineal DECIMAL(19, 2) NOT NULL,
    requiere_refuerzo BOOLEAN DEFAULT FALSE,
    imagen VARCHAR(500), -- Path o URL a PNG/PDF
    activo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- MATERIAL (Mármoles, Granitos, Dekton, etc.)
CREATE TABLE material (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_material VARCHAR(100) NOT NULL,
    largo_placa DECIMAL(10, 2) NOT NULL, -- Ej: 3.2m
    ancho_placa DECIMAL(10, 2) NOT NULL, -- Ej: 1.4m
    peso_m2 DECIMAL(10, 2) NOT NULL, -- kg/m²
    tiene_vetas BOOLEAN DEFAULT FALSE,
    terminacion_default_id BIGINT,
    precio_por_m2 DECIMAL(19, 2) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (terminacion_default_id) REFERENCES terminacion(id) ON DELETE SET NULL,
    UNIQUE KEY uk_material_placa (nombre_material, largo_placa, ancho_placa)
);

-- TIPO_TRABAJO_MATERIAL (Catálogo: Mesada Lineal, Mesada L, Bacha Armada, etc.)
CREATE TABLE tipo_trabajo_material (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(500),
    activo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TIPO_MANO_OBRA (Catálogo: Armado, Pulido, Corte, etc. - FUTURO)
CREATE TABLE tipo_mano_obra (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(500),
    costo_base DECIMAL(19, 2), -- Costo por defecto (puede variar)
    activo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- 2. ENTIDADES PRINCIPALES
-- =====================================================

-- CLIENTE
CREATE TABLE cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100),
    fecha_ultima_venta DATE,
    activo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- PROYECTO (Contiene el Presupuesto)
CREATE TABLE proyecto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    direccion VARCHAR(300),
    tiene_ascensor BOOLEAN DEFAULT FALSE,
    fecha_creacion DATE NOT NULL,
    precio_total_proyecto DECIMAL(19, 2) DEFAULT 0.00,
    descuento_proyecto DECIMAL(5, 2) DEFAULT 0.00, -- Porcentaje (0-100)
    precio_final DECIMAL(19, 2) DEFAULT 0.00,
    estado ENUM('PRESUPUESTO', 'CONFIRMADO', 'EN_EJECUCION', 'TERMINADO', 'CANCELADO') DEFAULT 'PRESUPUESTO',
    imagen_plano VARCHAR(500), -- Path o URL
    activo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE RESTRICT,
    INDEX idx_cliente_id (cliente_id)
);

-- AMBIENTE (Dentro de un Proyecto: Cocina, Baño, etc.)
CREATE TABLE ambiente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    proyecto_id BIGINT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    precio_ambiente DECIMAL(19, 2) DEFAULT 0.00,
    descuento_ambiente DECIMAL(5, 2) DEFAULT 0.00, -- Porcentaje (0-100)
    precio_final_ambiente DECIMAL(19, 2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (proyecto_id) REFERENCES proyecto(id) ON DELETE CASCADE,
    INDEX idx_proyecto_id (proyecto_id)
);

-- =====================================================
-- 3. TRABAJOS (Inheritance: Single Table Strategy)
-- =====================================================

-- TRABAJO (Base para Mano de Obra, Adicionales y Trabajos con Material)
CREATE TABLE trabajo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ambiente_id BIGINT NOT NULL,
    tipo ENUM('MANO_OBRA', 'ADICIONAL', 'TRABAJO_MATERIAL') NOT NULL,
    descripcion VARCHAR(500),
    precio DECIMAL(19, 2) NOT NULL,
    descuento DECIMAL(5, 2) DEFAULT 0.00, -- Porcentaje (0-100)
    precio_final DECIMAL(19, 2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (ambiente_id) REFERENCES ambiente(id) ON DELETE CASCADE,
    INDEX idx_ambiente_id (ambiente_id),
    INDEX idx_tipo (tipo)
);

-- =====================================================
-- 4. DETALLES DE TRABAJOS CON MATERIAL
-- =====================================================

-- TRABAJO_MATERIAL_DETALLE (N:1 con TRABAJO, M:N con MATERIAL y TERMINACIÓN)
CREATE TABLE trabajo_material_detalle (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    trabajo_id BIGINT NOT NULL,
    material_id BIGINT NOT NULL,
    terminacion_id BIGINT,
    tipo_trabajo_material_id BIGINT,
    largo DECIMAL(10, 2) NOT NULL,
    ancho DECIMAL(10, 2) NOT NULL,
    m2_calculado DECIMAL(10, 2) GENERATED ALWAYS AS (largo * ancho) STORED,
    ml_calculado DECIMAL(10, 2) GENERATED ALWAYS AS ((largo + ancho) * 2) STORED,
    merma_porcentaje DECIMAL(5, 2) DEFAULT 0.00, -- % de desperdicio
    imagen_isometrica VARCHAR(500), -- Path o URL PNG/PDF
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (trabajo_id) REFERENCES trabajo(id) ON DELETE CASCADE,
    FOREIGN KEY (material_id) REFERENCES material(id) ON DELETE RESTRICT,
    FOREIGN KEY (terminacion_id) REFERENCES terminacion(id) ON DELETE SET NULL,
    FOREIGN KEY (tipo_trabajo_material_id) REFERENCES tipo_trabajo_material(id) ON DELETE SET NULL,
    INDEX idx_trabajo_id (trabajo_id),
    INDEX idx_material_id (material_id)
);

-- TRABAJO_MATERIAL_BORDE (M:N: Un Trabajo puede tener múltiples bordes)
CREATE TABLE trabajo_material_borde (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    trabajo_material_detalle_id BIGINT NOT NULL,
    borde_id BIGINT NOT NULL,
    metraje_aplicado DECIMAL(10, 2) NOT NULL, -- ML de borde
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (trabajo_material_detalle_id) REFERENCES trabajo_material_detalle(id) ON DELETE CASCADE,
    FOREIGN KEY (borde_id) REFERENCES borde(id) ON DELETE RESTRICT,
    INDEX idx_trabajo_material_detalle_id (trabajo_material_detalle_id)
);

-- =====================================================
-- ÍNDICES ADICIONALES PARA PERFORMANCE
-- =====================================================
CREATE INDEX idx_material_activo ON material(activo);
CREATE INDEX idx_borde_activo ON borde(activo);
CREATE INDEX idx_terminacion_activo ON terminacion(activo);
CREATE INDEX idx_proyecto_estado ON proyecto(estado);
CREATE INDEX idx_proyecto_fecha ON proyecto(fecha_creacion);
CREATE INDEX idx_cliente_email ON cliente(email);

-- =====================================================
-- CONSTRAINTS Y CONSIDERACIONES
-- =====================================================
/*
NOTAS CRÍTICAS:

1. BigDecimal en Java → DECIMAL(19,2) en MySQL
   - 19 dígitos totales, 2 decimales
   - Soporta valores hasta 9,999,999,999,999,999.99

2. Single Table Inheritance (STI)
   - Tabla TRABAJO con discriminador TIPO
   - Simplifica queries y relationships
   - JPA @Inheritance(strategy=InheritanceType.SINGLE_TABLE)

3. Cálculos Derivados
   - m2_calculado: largo × ancho (GENERATED ALWAYS AS)
   - ml_calculado: (largo + ancho) × 2 (GENERATED ALWAYS AS)
   - precio_final: precio - (precio × descuento/100) → Calcular en APP

4. Mermas (TODAVÍA POR DEFINIR)
   - merma_porcentaje: % de desperdicio por pieza
   - Lógica: A implementar en Servicio de Negocio

5. Imágenes
   - Almacenar path/URL, no el archivo en BD
   - Ej: "/images/isometricos/mesada-l-dekton.png"
   - Ej: "https://cdn.cotizador.local/bordes/inglete-45.png"

6. Estados de Proyecto
   - PRESUPUESTO: Presupuesto inicial
   - CONFIRMADO: Cliente aceptó
   - EN_EJECUCION: En taller
   - TERMINADO: Completado
   - CANCELADO: Cancelado

7. Relaciones Críticas
   - Cliente → Proyecto: 1:N (un cliente, muchos proyectos)
   - Proyecto → Ambiente: 1:N (un proyecto, muchos ambientes)
   - Ambiente → Trabajo: 1:N (un ambiente, muchos trabajos)
   - Trabajo → TrabajoMaterialDetalle: 1:N (un trabajo, muchos materiales)
   - TrabajoMaterialDetalle → Material: N:1
   - TrabajoMaterialDetalle → Borde: M:N (a través de TRABAJO_MATERIAL_BORDE)
*/

