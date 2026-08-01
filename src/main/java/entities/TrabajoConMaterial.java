package entities;

import jakarta.persistence.*;
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

public class TrabajoConMaterial extends Trabajo {

    @NotNull(message = "El largo no puede estar vacío")
    @Column(name = "largo", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "El largo debe ser mayor que cero")
    private BigDecimal largo;

    @NotNull(message = "El ancho no puede estar vacío")
    @Column(name = "ancho", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "El ancho debe ser mayor que cero")
    private BigDecimal ancho;

    @NotNull(message = "El material no puede estar vacío")
    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

}
