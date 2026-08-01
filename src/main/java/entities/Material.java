package entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="materiales")

public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre del material no puede estar vacío") // CORRECCIÓN APLICADA
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @NotNull(message = "El largo de la placa no puede ser nulo") // CORRECCIÓN APLICADA
    @Column(name = "largo_placa", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "El largo de la placa debe ser mayor que 0")
    private BigDecimal largoPlaca;
    
    @NotNull(message = "El ancho de la placa no puede ser nulo") // CORRECCIÓN APLICADA
    @Column(name = "ancho_placa", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "El ancho de la placa debe ser mayor que 0")
    private BigDecimal anchoPlaca;
    
    @NotNull(message = "El peso por metro cuadrado no puede ser nulo") // CORRECCIÓN APLICADA
    @Column(name = "peso_m2", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "El peso por metro cuadrado debe ser mayor que 0")
    private BigDecimal pesoM2;
    
    @NotNull(message = "El precio por metro cuadrado no puede ser nulo") // CORRECCIÓN APLICADA
    @Column(name = "precio_por_m2", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio por metro cuadrado debe ser mayor que 0")
    private BigDecimal precioPorM2;
    
    @NotNull(message = "El precio por placa no puede ser nulo") // CORRECCIÓN APLICADA
    @Column(name = "precio_por_placa", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio por placa debe ser mayor que 0")
    private BigDecimal precioPorPlaca;

    @NotNull(message = "La terminación no puede ser nula")
    @ManyToOne
    @JoinColumn(name = "terminacion_id", nullable = false)
    private TipoTerminacion terminacion;

    @NotNull(message = "Se debe especificar si es un recorte") // CORRECCIÓN APLICADA
    @Column(name = "es_recorte", nullable = false)
    private Boolean esRecorte;
}