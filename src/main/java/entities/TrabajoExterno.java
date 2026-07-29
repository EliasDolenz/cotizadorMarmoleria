package entities;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TrabajoExterno extends Trabajo{
    @NotNull(message = "El costo no puede estar vacío")
    @Column(name = "precio", nullable = false)
    private BigDecimal precioServicio;
}
