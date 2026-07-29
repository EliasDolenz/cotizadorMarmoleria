package entities;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TrabajoConManoDeObra extends  Trabajo{
    @NotNull
    @Column(name = "horas", nullable = false)
    private Integer horas;

    @NotNull
    @Column(name = "costoPorHora", nullable = false)
    private BigDecimal costoPorHora;

    @NotNull
    @Column (name = "costoInsumos", nullable = false)
    private BigDecimal costoInsumos;

}