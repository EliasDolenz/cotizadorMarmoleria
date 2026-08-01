package entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="ambientes")
public class Ambiente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del ambiente no puede estar vacío") // CORRECCIÓN APLICADA
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull(message = "El descuento general no puede ser nulo") // CORRECCIÓN APLICADA
    @Column(name = "descuento_general", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.0", inclusive = true, message = "El descuento general debe ser mayor o igual a cero")
    private BigDecimal descuentoGeneral = BigDecimal.ZERO;


    @OneToMany(mappedBy = "ambiente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trabajo> trabajos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "proyecto_id", nullable = false)
    private Proyecto proyecto;

}