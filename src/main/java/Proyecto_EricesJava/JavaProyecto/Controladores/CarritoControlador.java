package Proyecto_EricesJava.JavaProyecto.Controladores;

import Proyecto_EricesJava.JavaProyecto.Entidades.ProductoCarrito;
import Proyecto_EricesJava.JavaProyecto.Servicios.CarritoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
public class CarritoControlador {

    @Autowired
    private CarritoServicio carritoServicio;

    @GetMapping("/{clienteId}")
    public ResponseEntity<List<ProductoCarrito>> obtenerCarritoPorClienteId(@PathVariable Long clienteId) {
        try {
            List<ProductoCarrito> productos = carritoServicio.obtenerProductosDeCarrito(clienteId);
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{carritoId}/products/{productoId}/{cantidad}")
    public ResponseEntity<ProductoCarrito> agregarProductoACarrito(@PathVariable Long carritoId, @PathVariable Long productoId, @PathVariable Integer cantidad) {
        try {
            ProductoCarrito productoCarrito = carritoServicio.agregarProductoACarrito(carritoId, productoId, cantidad);
            return new ResponseEntity<>(productoCarrito, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{productoCarritoId}")
    public ResponseEntity<Void> eliminarProductoDeCarrito(@PathVariable Long productoCarritoId) {
        try {
            carritoServicio.eliminarProductoDeCarrito(productoCarritoId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
