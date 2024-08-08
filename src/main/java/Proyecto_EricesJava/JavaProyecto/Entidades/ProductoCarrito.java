package Proyecto_EricesJava.JavaProyecto.Entidades;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCarrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;
    private boolean delivered = false;

    @ManyToOne
    private Producto producto;

    @ManyToOne
    private Carrito carrito;
}
