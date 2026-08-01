package entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="trabajos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Añadido para STI
@DiscriminatorColumn(name = "tipo_trabajo", discriminatorType = DiscriminatorType.STRING) // Añadido para el discriminador
public abstract class Trabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "descuento", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.0", inclusive = true, message = "El descuento debe ser mayor o igual a cero")
    private BigDecimal descuento;

    @ManyToOne
    @JoinColumn(name = "ambiente_id", nullable = false)
    private Ambiente ambiente;


}
