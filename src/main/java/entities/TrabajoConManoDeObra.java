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
public class TrabajoConManoDeObra extends  Trabajo{
    @NotNull(message = "Las horas no pueden estar vacías")
    @Column(name = "horas", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Las horas deben ser mayor que cero")
    private BigDecimal horas;

    @NotNull(message = "El costo por hora no puede estar vacío")
    @Column(name = "costo_por_hora", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "El costo por hora debe ser mayor que cero")
    private BigDecimal costoPorHora;

    @NotNull(message = "El costo de insumos no puede estar vacío")
    @Column (name = "costo_insumos", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "El costo de insumos debe ser mayor que cero")
    private BigDecimal costoInsumos;

}