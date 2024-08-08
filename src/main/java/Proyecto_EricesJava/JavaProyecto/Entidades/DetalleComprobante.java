package Proyecto_EricesJava.JavaProyecto.Entidades;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleComprobante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidad;
    private Double precioUnitario;

    @ManyToOne
    private Producto producto;

    @ManyToOne
    private Comprobante comprobante;
}
