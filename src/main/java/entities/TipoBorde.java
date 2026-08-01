package entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tipos_de_bordes")

public class TipoBorde {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del borde no puede estar vacío")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "precio", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor o igual a cero")
    private BigDecimal precio;
}
