package entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "proyectos")


public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del proyecto no puede estar vacío") // CORRECCIÓN APLICADA
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank(message = "La dirección del proyecto no puede estar vacía") // CORRECCIÓN APLICADA
    @Column(name = "direccion", nullable = false)
    private String direccion;


    @Column(name = "piso", nullable = false)
    private Integer piso;

    @NotNull(message = "La fecha de presupuesto no puede ser nula") // CORRECCIÓN APLICADA
    @Column(name = "fecha_presupuesto")
    private LocalDate fechaPresupuesto = LocalDate.now();

    @NotNull(message = "El estado del proyecto no puede ser nulo") // CORRECCIÓN APLICADA
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoProyecto estado = EstadoProyecto.EN_PRESUPUESTO;

    @NotNull(message = "El descuento general no puede ser nulo") // CORRECCIÓN APLICADA
    @Column(name = "descuento_general", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.0", inclusive = true, message = "El descuento general debe ser mayor o igual a cero")
    private BigDecimal descuentoGeneral = BigDecimal.ZERO;

    @NotNull(message = "Se debe especificar si aplica IVA") // CORRECCIÓN APLICADA
    @Column(name = "tiene_Iva", nullable = false)
    private Boolean conIva = Boolean.TRUE;

    @NotNull(message = "El porcentaje de IVA no puede ser nulo") // CORRECCIÓN APLICADA
    @Column(name = "porcentaje_iva", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.0", inclusive = true, message = "El porcentaje de IVA debe ser mayor o igual a cero")
    private BigDecimal iva = new BigDecimal("0.21");

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ambiente> ambientes = new ArrayList<>();

}