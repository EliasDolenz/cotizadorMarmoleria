package entities;

import jakarta.persistence.*;
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
    
    @NotBlank
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @NotNull
    @Column(name = "largoPlaca", nullable = false)
    private Double largoPlaca;
    
    @NotNull
    @Column(name = "anchoPlaca", nullable = false)
    private Double anchoPlaca;
    
    @NotNull
    @Column(name = "pesoM2", nullable = false)
    private Double pesoM2;
    
    @NotNull
    @Column(name = "precioPorM2", nullable = false)
    private BigDecimal precioPorM2;
    
    @NotNull
    @Column(name = "precioPorPlaca", nullable = false)
    private BigDecimal precioPorPlaca;
    
    @NotNull
    @Column(name = "terminación", nullable = false)
    private TipoTerminacion terminacion;

    @NotNull
    @Column(name = "esRecorte", nullable = false)
    private Boolean esRecorte;
}
