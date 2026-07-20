## Diagrama Entidad-Relación Visual (DER)

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                    COTIZADOR PARAMÉTRICO - DIAGRAMA E-R                     │
└─────────────────────────────────────────────────────────────────────────────┘

                              CATÁLOGOS (Datos Maestros)
                              ═════════════════════════════

    ┌──────────────┐      ┌──────────────┐      ┌──────────────┐
    │ Terminación  │      │    Borde     │      │  Material    │
    ├──────────────┤      ├──────────────┤      ├──────────────┤
    │ id (PK)      │      │ id (PK)      │      │ id (PK)      │
    │ nombre       │      │ nombre       │      │ nombre       │
    │ descripción  │      │ descripción  │      │ largoPlaca   │
    │ precioAdiM2  │      │ costoPorML   │      │ anchoPlaca   │
    │ activo       │      │ reqRefuerzo  │      │ pesoM2       │
    └──────────────┘      │ imagen       │      │ tieneVetas   │
                          │ activo       │      │ termDef (FK) │ ──┐
                          └──────────────┘      │ precioPorM2  │   │
                                               │ activo       │   │
                          ┌──────────────┐      └──────────────┘   │
                          │TipoTrabajo   │                         │
                          │Material      │                         │
                          ├──────────────┤                         │
                          │ id (PK)      │                         │
                          │ nombre       │                         │
                          │ descripción  │                         │
                          │ activo       │                         │
                          └──────────────┘                         │
                                                                   │
                                                                   └──────┐
                                                                          │
                              ENTIDADES PRINCIPALES                      │
                              ══════════════════════════                  │
                                                                          │
    ┌──────────────────────────────────────────────────────────┐         │
    │                         Cliente                          │         │
    ├──────────────────────────────────────────────────────────┤         │
    │ id (PK)                                                  │         │
    │ nombre, telefono, email                                  │         │
    │ fechaUltimaVenta                                         │         │
    │ cantVentasConcretadas (derivada)                         │         │
    │ cantPresupuestos (derivada)                              │         │
    │ esClienteHabitual (derivada)                             │         │
    └──────────────────────────────────────────────────────────┘         │
                              │                                           │
                              │ 1:N                                       │
                              │                                           │
                              ▼                                           │
    ┌──────────────────────────────────────────────────────────┐         │
    │                       Proyecto                           │         │
    ├──────────────────────────────────────────────────────────┤         │
    │ id (PK)                                                  │         │
    │ cliente_id (FK)                                          │         │
    │ direccion, tieneAscensor, fecha                          │         │
    │ precioTotalProyecto, descuentoProyecto, precioFinal     │         │
    │ estado (PRESUPUESTO, CONFIRMADO, EN_EJECUCION, etc.)    │         │
    │ imagenPlano                                              │         │
    └──────────────────────────────────────────────────────────┘         │
                              │                                           │
                              │ 1:N                                       │
                              │                                           │
                              ▼                                           │
    ┌──────────────────────────────────────────────────────────┐         │
    │                       Ambiente                           │         │
    ├──────────────────────────────────────────────────────────┤         │
    │ id (PK)                                                  │         │
    │ proyecto_id (FK)                                         │         │
    │ nombre (Cocina, Baño, etc.)                              │         │
    │ precioAmbiente, descuentoAmbiente, precioFinalAmbiente   │         │
    └──────────────────────────────────────────────────────────┘         │
                              │                                           │
                              │ 1:N                                       │
                              │                                           │
                              ▼                                           │
    ┌──────────────────────────────────────────────────────────┐         │
    │                       Trabajo                            │         │
    │              (Single Table Inheritance)                  │         │
    ├──────────────────────────────────────────────────────────┤         │
    │ id (PK)                                                  │         │
    │ ambiente_id (FK)                                         │         │
    │ tipo (DISCRIMINATOR)                                     │         │
    │   - MANO_OBRA                                            │         │
    │   - ADICIONAL                                            │         │
    │   - TRABAJO_MATERIAL                                     │         │
    │ descripcion                                              │         │
    │ precio, descuento, precioFinal                           │         │
    └──────────────────────────────────────────────────────────┘         │
                              │                                           │
                   ┌──────────┴──────────┐                                │
                   │                     │                                │
              MANO_OBRA             TRABAJO_MATERIAL                      │
              (sin material)        (con material)                        │
                                         │                                │
                                         │ 1:N                           │
                                         │                                │
                                         ▼                                │
    ┌──────────────────────────────────────────────────────────┐         │
    │          TrabajoMaterialDetalle                          │         │
    ├──────────────────────────────────────────────────────────┤         │
    │ id (PK)                                                  │         │
    │ trabajo_id (FK)                                          │         │
    │ material_id (FK) ◄────────────────────────────────────────┤─────┐  │
    │ terminacion_id (FK) ◄──────────────────────────────────────────┼──┤
    │ tipoTrabajoMaterial_id (FK)                              │    │  │
    │ largo, ancho                                             │    │  │
    │ m2_calculado (GENERATED)                                 │    │  │
    │ ml_calculado (GENERATED)                                 │    │  │
    │ mermaPorc                                                │    │  │
    │ imagenIsometrica                                         │    │  │
    └──────────────────────────────────────────────────────────┘    │  │
                              │                                      │  │
                              │ 1:N                                  │  │
                              │                                      │  │
                              ▼                                      │  │
    ┌──────────────────────────────────────────────────────────┐   │  │
    │         TrabajoMaterialBorde (M:N Bridge)               │   │  │
    ├──────────────────────────────────────────────────────────┤   │  │
    │ id (PK)                                                  │   │  │
    │ trabajoMaterialDetalle_id (FK)                           │   │  │
    │ borde_id (FK) ◄────────────────────────────────────────────│──┘  │
    │ metrajeAplicado (ML)                                     │      │
    └──────────────────────────────────────────────────────────┘      │
                                                                       │
                                                                   FK a
                                                                Terminación
                                                                   (vía
                                                                Material)
```

---

## Relaciones Clave

| De | A | Cardinalidad | Nota |
|---|---|---|---|
| Cliente | Proyecto | 1:N | Un cliente puede tener muchos proyectos |
| Proyecto | Ambiente | 1:N | Un proyecto tiene múltiples ambientes |
| Ambiente | Trabajo | 1:N | Un ambiente tiene múltiples trabajos |
| Trabajo | TrabajoMaterialDetalle | 1:N | Solo si tipo=TRABAJO_MATERIAL |
| TrabajoMaterialDetalle | Material | N:1 | Muchas líneas pueden usar el mismo material |
| TrabajoMaterialDetalle | Terminación | N:1 | Puede modificar la terminación del material |
| TrabajoMaterialDetalle | Borde | M:N | Un trabajo puede tener múltiples bordes |
| Material | Terminación | N:1 | Cada material tiene terminación por defecto |

---

## Atributos Calculados (NO Almacenados)

- **m2_calculado**: `largo × ancho` (GENERATED ALWAYS AS en BD)
- **ml_calculado**: `(largo + ancho) × 2` (GENERATED ALWAYS AS en BD)
- **precioFinal (Trabajo)**: `precio - (precio × descuento/100)`
- **precioAmbiente**: `SUM(trabajo.precioFinal)` para el ambiente
- **precioTotalProyecto**: `SUM(ambiente.precioFinal)`
- **cantVentasConcretadas (Cliente)**: COUNT(proyectos con estado=TERMINADO)
- **esClienteHabitual (Cliente)**: `cantVentas > 1 AND (ahora - fechaUltimaVenta) < 6 meses`

---

## Tipos de Datos Críticos

| Campo | Tipo SQL | Tipo Java | Razón |
|---|---|---|---|
| `precio*`, `costo*` | DECIMAL(19,2) | BigDecimal | Precisión financiera exacta |
| `descuento*` | DECIMAL(5,2) | BigDecimal | Porcentaje 0-100 |
| `largo`, `ancho`, `m2` | DECIMAL(10,2) | BigDecimal | Medidas en metros |
| `mermaPorc` | DECIMAL(5,2) | BigDecimal | Porcentaje 0-100 |
| `estado` | ENUM | Enum Java | Estados del Proyecto |
| `tipo` | ENUM | Enum Java | Discriminador de Trabajo |

---

## Notas de Implementación

1. **Single Table Inheritance**: Todos los tipos de `Trabajo` en la misma tabla con discriminador `tipo`.
2. **Bordes Múltiples**: Puedes asociar varios bordes a un mismo `TrabajoMaterialDetalle`.
3. **Mermas**: Campo reservado para futura lógica (todavía no definida).
4. **Imágenes**: Almacenar como paths/URLs, no como BLOBs en la BD.
5. **Cascada**: Eliminar un Proyecto elimina sus Ambientes y Trabajos automáticamente.
6. **Índices**: Creados en FK y campos frecuentes (estado, activo) para performance.


