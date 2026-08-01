package entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "configuracion_empresa")
public class ConfiguracionEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El coeficiente de ganancia no puede ser nulo")
    @Column(name = "coeficiente_ganancia", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "1.0", inclusive = false, message = "El coeficiente de ganancia debe ser mayor que 1.0")
    private BigDecimal coeficienteGanancia;
}
