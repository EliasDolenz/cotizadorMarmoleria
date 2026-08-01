package entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tipos_de_terminaciones")
public class TipoTerminacion {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la terminación no puede estar vacío")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull(message = "El precio no puede estar vacío")
    @Column(name = "precio", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor o igual a cero")
    private BigDecimal precio;

}
