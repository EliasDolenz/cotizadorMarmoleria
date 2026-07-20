# Contexto Crítico del Proyecto: Cotizador

## 👤 Perfil del Desarrollador
- **Nombre**: Elías
- **Trasfondo**: Maestro Mayor de Obras (MMO)
- **Experiencia Real**: Trabaja actualmente en una marmolería
- **Habilidades**: Lectura de planos, despieces, modelado 3D (SketchUp/AutoCAD)
- **Disponibilidad**: 5-10 horas semanales
- **Computadora**: No muy poderosa → Evitar overhead innecesario

---

## 🎯 Objetivo del Proyecto (NORTE ABSOLUTO)
**NO es**: Aplicación rápida/No-Code, facturación inmediata.
**ES**: **Pieza de ingeniería de software de alto valor** para portafolio profesional/académico.

**Demostrar a reclutadores:**
- Modelado de lógica de negocio compleja (mermas, despieces, cálculos geométricos).
- Manejo seguro de transacciones financieras (`BigDecimal`).
- Resolución de problemas del mundo real con código puro.

---

## 🏗️ Stack Técnico (CONFIRMADO)
- **Backend**: Java 21 + Spring Boot 4.0.7
- **Persistencia**: Spring Data JPA + Flyway (versionado de BD)
- **Base de Datos**: MySQL 8.4.0
- **Frontend**: React (fase posterior)
- **Build**: Maven
- **Testing**: JUnit + H2 (base de datos en memoria)

### Dependencias POM Actuales
✅ `spring-boot-starter-data-jpa`
✅ `spring-boot-starter-flyway` + `flyway-mysql`
✅ `spring-boot-starter-security`
✅ `spring-boot-starter-validation`
✅ `spring-boot-starter-web` (Spring MVC)
✅ `mysql-connector-j`
✅ `lombok`
✅ `spring-boot-devtools`
✅ Dependencias de test

---

## 🔄 Metodología de Trabajo (ESTRICTO)
1. **Una sola cosa a la vez**.
2. **NO avanzar al siguiente paso hasta que el actual esté:**
   - Compilado ✓
   - Probado ✓
   - Consolidado ✓
3. Documentar cada decisión en `PROGRESS.md`.

---

## ### 📊 El Dominio: Cotizador para Marmolería

#### Cliente
- Distribuidora de mármoles + 7 marmolerías socias.

#### Funcionalidades Nucleares
1. **Estructuras Base** (presets):
   - Lineales (mesadas comunes)
   - En L, en U
   - Islas (ciegas, con vuelo, con cascadas, revestidas)

2. **Modificadores y Terminaciones**:
   - Alzadas/Zócalos
   - Revestimientos
   - Frentes
   - **4+ Tipos de Bordes**: Regrueso a 2cm, inglete a 45°, pulido simple, etc.
   - Cada uno añade costo por metro lineal

3. **Motor de Cálculo**:
   - Cálculo estricto de superficies (m²)
   - **Cálculo de mermas** (desperdicio de placas) → CRÍTICO
   - Alertas de viabilidad técnica:
     - ¿Pieza supera tamaño de placa base?
     - ¿Necesita refuerzos estructurales?
   - Cálculo logístico: peso, dimensiones, acarreo complejo (escaleras, etc.)

4. **Salida**:
   - Presupuesto formal en PDF (para cliente)
   - Orden de taller

#### Visualización (Estrategia Anti-Explosión Combinatoria)
**Problema**: Estructuras × Colores × Bordes × Bachas = Miles de imágenes

**Solución**:
- **Imágenes Isométricas Fijas**: Renders limpios (SketchUp) de estructuras base (L, U, Isla con cascada).
- **Módulos Aislados**: Bordes y terminaciones en recuadro lateral independiente.
- Evita fusión gráfica; solo composición en UI.

---

## 💾 Decisiones de Infraestructura

### Docker Compose
- ❌ **NO USAR POR AHORA** (computadora débil, proyecto recién comienza).
- `docker-compose.yml` está configurado y listo.
- **Se activará en**: Fase de testing de integración contra BD real MySQL.

### Base de Datos para Desarrollo
- ✅ **H2 Database** (en memoria, desarrollo/testing).
- Luego → MySQL real cuando sea necesario testing real o computadora upgraded.

### Migraciones
- Flyway + MySQL driver ya configurados.
- Scripts en `src/main/resources/db/migration/`.
- Se ejecutarán cuando conectemos BD real.

---

## 🚦 Próximos Pasos (Orden Estricto)

### ✅ Completado
1. Proyecto Spring Initializr creado.
2. Dependencias configuradas.
3. Docker Compose listo (sin usar aún).
4. Contexto y decisiones documentadas.
5. **Diseño del DER completado (2026-07-20)**
   - Archivos generados: `SCHEMA.sql`, `DER.md`
   - Catálogos: Terminación, Borde, Material, TipoTrabajoMaterial, TipoManoObra
   - Entidades: Cliente, Proyecto, Ambiente, Trabajo (con STI), TrabajoMaterialDetalle, TrabajoMaterialBorde

### 🔜 Siguiente (AHORA)
1. **Crear entidades JPA en Java** (paso a paso, una a una).
   - Empezar con catálogos (Terminación, Borde, Material).
   - Luego entidades principales (Cliente, Proyecto, Ambiente, Trabajo).
   - Finalmente detalles (TrabajoMaterialDetalle, TrabajoMaterialBorde).

---

## 📝 Nota Importante: BigDecimal y Precisión Financiera
- **SIEMPRE usar `BigDecimal`** para dinero, márgenes, costos.
- **NUNCA usar `double` o `float`** en cálculos financieros.
- En BD: `DECIMAL(19,2)` (19 dígitos totales, 2 decimales).
- Ejemplo: Margen de ganancia, costo por m², merma %.

---

## 📊 Diseño del DER (CONFIRMADO - 2026-07-20)

### Estrategia Clave: Single Table Inheritance (STI)
- **Tabla Trabajo** única con discriminador `tipo` (MANO_OBRA, ADICIONAL, TRABAJO_MATERIAL)
- Simplifica queries y relaciones
- Cada tipo hereda los campos base

### Entidades Principales
1. **Cliente**: Datos de cliente, contador de ventas, fecha última venta
2. **Proyecto**: Contiene ambientes, descuentos, estado
3. **Ambiente**: Cocina, baño, etc. Agrupa trabajos
4. **Trabajo** (STI): Base para tres tipos
   - **ManoDeObra**: Sin material
   - **Adicional**: Gastos (transporte, instalación)
   - **TrabajoConMaterial**: Con material, bordes, terminación
5. **TrabajoMaterialDetalle**: Vincula trabajo con material (1:N)
6. **TrabajoMaterialBorde**: M:N entre TrabajoMaterialDetalle y Borde

### Catálogos (Datos Maestros)
- **Material**: Nombre, largoPlaca, anchoPlaca, pesoM2, precioPorM2
- **Terminación**: Pulido, Flameado, etc. + costo adicional
- **Borde**: Regrueso, Inglete, etc. + costoPorML + requiereRefuerzo
- **TipoTrabajoMaterial**: Mesada Lineal, L, U, Isla, Bacha Armada, etc.
- **TipoManoDeObra**: (Vacío inicialmente, se completa conforme necesites)

### Decisiones Importantes
- ✅ **Material con dimensiones de placa**: Permite distintos tamaños (Standard vs. Jumbo)
- ✅ **Terminación modificable por trabajo**: Default del material, pero puede cambiar
- ✅ **Múltiples bordes por trabajo**: Relación M:N
- ✅ **Descuentos granulares**: Trabajo, Ambiente, Proyecto (independientes)
- ✅ **Cálculos derivados**: m2, ml, precioFinal (GENERATED en BD o calculados en app)
- ⏳ **Mermas**: Campo reservado, lógica pendiente
- ✅ **Imágenes como paths/URLs**: No como BLOBs

### Archivos Generados
- **SCHEMA.sql**: DDL MySQL completo
- **DER.md**: Diagrama visual ASCII + tabla de relaciones

---

## 🔗 Archivos Críticos del Proyecto
- `PROGRESS.md` → Registro detallado de avances paso a paso.
- `CONTEXT.md` (este archivo) → Contexto y decisiones rápidas.
- `DER.md` → Diagrama Entidad-Relación visual con tablas de relaciones.
- `SCHEMA.sql` → DDL MySQL completo (tablas, índices, constraints).
- `pom.xml` → Dependencias Maven.
- `docker-compose.yml` → Configuración MySQL (sin usar aún).
- `src/main/resources/application.properties` → Configuración Spring.

---

**Última Actualización**: 2026-07-20  
**Estado**: DER Completado ✅ | Listos para Crear Entidades JPA

