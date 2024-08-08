package Proyecto_EricesJava.JavaProyecto.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comprobante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double total;
    private LocalDateTime fecha;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "comprobante", cascade = CascadeType.ALL)
    private List<DetalleComprobante> detalles;
}
