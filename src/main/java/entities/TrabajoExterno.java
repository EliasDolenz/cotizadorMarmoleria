package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TrabajoExterno extends Trabajo{
    @NotNull(message = "El costo no puede estar vacío")
    @Column(name = "precio", nullable = false,precision = 15, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que cero")
    private BigDecimal precioServicio;
}
