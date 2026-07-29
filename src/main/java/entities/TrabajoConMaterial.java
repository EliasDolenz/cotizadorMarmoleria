package entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="trabajos_con_material")

public class TrabajoConMaterial extends Trabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "largo", nullable = false)
    private Double largo;

    @Column(name = "ancho", nullable = false)
    private Double ancho;

    @NotNull
    private Material material;

    @Override
    public BigDecimal calcularCosto() {
        BigDecimal area = BigDecimal.valueOf(largo * ancho);
        BigDecimal costoMaterial = material.getCostoPorMetroCuadrado().multiply(area);
        return costoMaterial.multiply(BigDecimal.valueOf(1 - descuento));
    }


}
