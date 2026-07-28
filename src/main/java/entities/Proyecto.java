package entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

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

    @NotBlank
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotNull
    @Column(name = "piso")
    private Integer pisos;

    @NotNull
    @Column(name = "fecha_presupuesto")
    private Date fechaPresupuesto = new Date();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoProyecto estado = EstadoProyecto.EN_PRESUPUESTO;

    @NotNull
    @Column(name = "descuento_general", nullable = false)
    private Double descuentoGeneral = 0.0;

    @NotNull
    @Column(name = "tieneIva", nullable = false)
    private Boolean conIva = Boolean.TRUE;

    @NotNull
    @Column(name = "porcentajeIva", nullable = false)
    private Double iva = 0.21;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

}
