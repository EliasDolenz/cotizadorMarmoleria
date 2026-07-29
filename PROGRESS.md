# Bitácora de Avance del Proyecto: Cotizador

Este archivo documenta el progreso, las decisiones de diseño y los hitos alcanzados en el desarrollo del proyecto **Cotizador**.

## 🎯 Visión General
- **Desarrollador:** Elías (MMO).
- **Stack:** Java, Spring Boot 3.x, Spring Data JPA, MySQL, React.
- **Metodología:** Paso a paso estricto (1 sola cosa a la vez). Consolidar antes de avanzar.
- **Propósito:** Crear una pieza de ingeniería de software de alto valor, manejando lógica compleja (mermas, logística, transacciones seguras).

## 🏗️ Dominio del Negocio
- Cotizador paramétrico para red de marmolerías.
- Estructuras base (Lineales, L, U, Islas).
- Modificadores (Bordes, Alzadas, Revestimientos).
- Motor de cálculo: $m^2$, mermas, alertas técnicas y logística.

## 🚦 Estado Actual: Fase 3 - Implementación de Entidades JPA ✅
- [x] Definición de visión y alcance.
- [x] **Diseño del Diagrama Entidad-Relación (DER)** ← *Completado 2026-07-20*.
- [x] Definición de entidades núcleo.
- [x] Crear entidades JPA en Java.

## 🗓️ Registro de Avances

### 2026-07-21: Fase 3 - Implementación de Entidades JPA (Parte 1) ✅

#### Avances Clave
1.  **Refinamiento de UML y Entidades JPA**:
    *   Se estableció el enfoque de modelado UML previo a la implementación JPA.
    *   **Entidad `Cliente.java`**: Implementada y revisada. Incluye campos de negocio (`contadorDeVentas`, `fechaUltimaVenta`) y relación `OneToMany` con `Proyecto` consolidada.
    *   **Entidad `Trabajo.java`**: Implementada como `abstract class` (STI). Se corrigió el tipo de `descuento` a `BigDecimal` y se añadió el método abstracto `calcularPrecio()`.
    *   **Decisión `TipoBorde`**: Se determinó que `TipoBorde` será una **Entidad JPA** (clase) para permitir la configuración de precios por el usuario.
    *   **Configuración `BigDecimal`**: Se definió `precision = 15` y `scale = 2` para los campos `BigDecimal` en la base de datos.

#### Próximo Paso
- [ ] Continuar con la creación y revisión de entidades JPA en Java, comenzando por `Ambiente.java`.

### 2026-07-20: Fase 2 - Diseño del Diagrama Entidad-Relación (DER) ✅

#### Decisiones Clave Tomadas
1. **Herencia de Trabajo**: Single Table Inheritance (STI)
   - Tabla única `trabajo` con discriminador `tipo` (MANO_OBRA, ADICIONAL, TRABAJO_MATERIAL)
   - Simplifica queries y relaciones

2. **Material**: Dimensiones de Placa
   - `largo_placa`, `ancho_placa`: Dimensiones del material que se compra (Ej: 3.2m × 1.4m)
   - Diferentes tamaños con mismo material (Ej: Dekton Standard vs. Dekton Jumbo) → Registros separados
   - Precio por m²

3. **Terminación**: Atributo del Material + Modificable
   - Cada Material tiene Terminación por defecto
   - Un Trabajo puede usar distinta Terminación con costo adicional

4. **Borde**: Entidad Independiente
   - Múltiples bordes por Trabajo (relación M:N)
   - Costo por metro lineal
   - Pueden requerir refuerzos estructurales

5. **Mermas**: Pendiente de Definición
   - Campo `merma_porcentaje` en TrabajoMaterialDetalle
   - Lógica a implementar en Servicio de Negocio (% según tamaño/material)

6. **Bonificación Granular**: A nivel de Trabajo y Ambiente
   - `descuento_trabajo` (0-100 %)
   - `descuento_ambiente` (0-100 %)
   - `descuento_proyecto` (0-100 %)
   - Se aplican independientemente

7. **Imágenes**: Campos de Path/URL
   - `imagen_plano` (Proyecto): Path a CAD
   - `imagen_isometrica` (TrabajoMaterialDetalle): PNG o PDF
   - `imagen` (Borde): Ilustración del borde

#### Catálogos Creados
- ✅ `terminacion`: Pulido, Flameado, Honed, etc.
- ✅ `borde`: Regrueso 2cm, Inglete 45°, Pulido Simple, etc.
- ✅ `material`: Mármoles, Granitos, Dekton, etc.
- ✅ `tipo_trabajo_material`: Mesada Lineal, Mesada L, Mesada U, Isla, Bacha Armada Ingletada, etc.
- ✅ `tipo_mano_obra`: (Vacío por ahora - Se poblarán conforme se agreguen: Armado, Pulido, Corte, etc.)

#### Estructura Jerárquica Confirmada
```
Cliente
 └─ Proyecto (estado, dirección, tiene ascensor, descuento)
     └─ Ambiente (cocina, baño, etc., descuento)
         └─ Trabajo (precio, descuento)
             ├─ ManoDeObra (sin material)
             ├─ Adicional (gastos varios)
             └─ TrabajoConMaterial (1:N)
                 └─ TrabajoMaterialDetalle (M:N con Material, Terminación, Borde)
                     ├─ Material (nombre, dimensiones placa, peso/m², precio/m²)
                     ├─ Terminación (acabado, costo adicional)
                     └─ Borde (múltiples, costo por metro lineal)
```

#### Archivo Generado
- **`SCHEMA.sql`**: DDL completo del esquema MySQL con todas las tablas, índices, constraints y notas críticas.

#### Spring Initializr
- [x] Generación del proyecto con Spring Initializr.
  - **Java**: 21
  - **Spring Boot**: 4.0.7
  - **Build**: Maven

#### Dependencias Configuradas (POM.xml)
- [x] **Persistencia**: `spring-boot-starter-data-jpa` (ORM/JPA)
- [x] **BD y Versionado**:
  - `spring-boot-starter-flyway` (Migraciones)
  - `flyway-mysql` (Driver MySQL para Flyway)
  - `mysql-connector-j` (JDBC Driver)
- [x] **Seguridad**: `spring-boot-starter-security` (Autenticación/Autorización)
- [x] **Validación**: `spring-boot-starter-validation` (Bean Validation)
- [x] **REST API**: `spring-boot-starter-web` (Spring MVC)
- [x] **Utilidades**:
  - `lombok` (Reducción de boilerplate)
  - `spring-boot-devtools` (Hot reload)
- [x] **Testing**: Dependencias de test para JPA, Flyway, Security, Validation, Web

#### Docker Compose (`docker-compose.yml`)
- [x] Servicio MySQL configurado:
  - **Imagen**: MySQL 8.4.0
  - **Container**: `mysql-cotizador`
  - **Base de datos**: `cotizador_marmoleria`
  - **Puerto**: 3308 (host) → 3306 (container)
  - **Usuario root**: `root`
  - **Volumen**: `mysql_data` (persistencia de datos)

### Inicio y Contextualización
- [x] Creación de `PROGRESS.md`.
- [x] Definición del stack técnico y metodología de trabajo.
- [x] Establecimiento del objetivo principal: Código puro y lógica de negocio sólida.


---

## 📝 Resumen Ejecutivo del DER

**El DER está completo y listo para implementación en Java/JPA.**

### Estructura en 3 Niveles
1. **Catálogos**: Terminación, Borde, Material, TipoTrabajoMaterial, TipoManoDeObra
2. **Entidades Principales**: Cliente → Proyecto → Ambiente → Trabajo
3. **Detalles**: TrabajoMaterialDetalle + TrabajoMaterialBorde (relaciones N:N)

### Estrategia de Herencia
- **Single Table Inheritance (STI)** en tabla `Trabajo`
- Discriminador `tipo`: MANO_OBRA | ADICIONAL | TRABAJO_MATERIAL
- Cada tipo tiene sus propias responsabilidades

### Campos Críticos para Lógica de Negocio
- **BigDecimal**: TODOS los precios, costos, descuentos, porcentajes
- **Mermas**: merma_porcentaje (pendiente de lógica)
- **Imágenes**: Paths/URLs, no BLOBs
- **Cálculos Derivados**: m2, ml, precioFinal (GENERATED o calculados en app)

### Futuros Desarrollos (Sin Bloquear Arquitectura)
- TipoManoDeObra: Armado, Pulido, Corte, Sellado, VisitaTécnica, etc.
- Catálogos de Lotes/Proveedores (relación Material → Lote)
- Cálculo inteligente de mermas según tamaño/material
- Alertas de viabilidad técnica (refuerzos, tamaño máximo placa)

---
*Nota: Este archivo se actualizará periódicamente con los comentarios y avances realizados.*