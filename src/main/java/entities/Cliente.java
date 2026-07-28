package entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "clientes")

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @NotBlank(message = "El email no puede estar vacío")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @OneToMany(mappedBy = "cliente", cascade =CascadeType.ALL, orphanRemoval = true)
    private List<Proyecto> proyectos = new ArrayList<>();

}
