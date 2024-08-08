package Proyecto_EricesJava.JavaProyecto.Servicios;

import Proyecto_EricesJava.JavaProyecto.Entidades.Carrito;
import Proyecto_EricesJava.JavaProyecto.Entidades.Comprobante;
import Proyecto_EricesJava.JavaProyecto.Entidades.DetalleComprobante;
import Proyecto_EricesJava.JavaProyecto.Entidades.ProductoCarrito;
import Proyecto_EricesJava.JavaProyecto.Entidades.Producto;
import Proyecto_EricesJava.JavaProyecto.Excepciones.ResourceNotFoundException;
import Proyecto_EricesJava.JavaProyecto.Repositorios.CarritoRepositorio;
import Proyecto_EricesJava.JavaProyecto.Repositorios.ComprobanteRepositorio;
import Proyecto_EricesJava.JavaProyecto.Repositorios.ProductoCarritoRepositorio;
import Proyecto_EricesJava.JavaProyecto.Repositorios.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarritoServicio {

    @Autowired
    private CarritoRepositorio carritoRepositorio;

    @Autowired
    private ProductoCarritoRepositorio productoCarritoRepositorio;

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Autowired
    private ComprobanteRepositorio comprobanteRepositorio;

    public Carrito obtenerCarritoPorClienteId(Long clienteId) {
        Carrito carrito = carritoRepositorio.findByClienteId(clienteId);
        if (carrito == null) {
            throw new ResourceNotFoundException("Carrito no encontrado para el clienteId " + clienteId);
        }
        return carrito;
    }

    public ProductoCarrito agregarProductoACarrito(Long carritoId, Long productoId, Integer cantidad) {
        Carrito carrito = carritoRepositorio.findById(carritoId).orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado con id " + carritoId));
        Producto producto = productoRepositorio.findById(productoId).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id " + productoId));

        ProductoCarrito productoCarrito = new ProductoCarrito();
        productoCarrito.setCarrito(carrito);
        productoCarrito.setProducto(producto); // Este es el punto donde se usa Producto
        productoCarrito.setCantidad(cantidad);
        productoCarrito.setDelivered(false);

        return productoCarritoRepositorio.save(productoCarrito);
    }

    public void eliminarProductoDeCarrito(Long productoCarritoId) {
        if (!productoCarritoRepositorio.existsById(productoCarritoId)) {
            throw new ResourceNotFoundException("ProductoCarrito no encontrado con id " + productoCarritoId);
        }
        productoCarritoRepositorio.deleteById(productoCarritoId);
    }

    public List<ProductoCarrito> obtenerProductosDeCarrito(Long clienteId) {
        Carrito carrito = carritoRepositorio.findByClienteId(clienteId);
        if (carrito == null) {
            throw new ResourceNotFoundException("Carrito no encontrado para el clienteId " + clienteId);
        }
        return carrito.getProductos().stream().filter(pc -> !pc.isDelivered()).collect(Collectors.toList());
    }

    public Comprobante generarComprobante(Long clienteId) {
        Carrito carrito = carritoRepositorio.findByClienteId(clienteId);
        if (carrito == null) {
            throw new ResourceNotFoundException("Carrito no encontrado para el clienteId " + clienteId);
        }

        List<ProductoCarrito> productosNoEntregados = carrito.getProductos().stream()
                .filter(pc -> !pc.isDelivered())
                .collect(Collectors.toList());

        if (productosNoEntregados.isEmpty()) {
            throw new RuntimeException("No hay productos no entregados en el carrito.");
        }

        double total = productosNoEntregados.stream()
                .mapToDouble(pc -> pc.getProducto().getPrecio() * pc.getCantidad())
                .sum();

        Comprobante comprobante = new Comprobante();
        comprobante.setCliente(carrito.getCliente());
        comprobante.setTotal(total);
        comprobante.setFecha(LocalDateTime.now());
        comprobante.setDetalles(productosNoEntregados.stream().map(pc -> {
            DetalleComprobante detalle = new DetalleComprobante();
            detalle.setProducto(pc.getProducto());
            detalle.setCantidad(pc.getCantidad());
            detalle.setPrecioUnitario(pc.getProducto().getPrecio());
            detalle.setComprobante(comprobante);
            return detalle;
        }).collect(Collectors.toList()));

        productosNoEntregados.forEach(pc -> pc.setDelivered(true));
        productoCarritoRepositorio.saveAll(productosNoEntregados);

        return comprobanteRepositorio.save(comprobante);
    }

    public Comprobante obtenerUltimoComprobante(Long clienteId) {
        return comprobanteRepositorio.findTopByClienteIdOrderByFechaDesc(clienteId);
    }
}
