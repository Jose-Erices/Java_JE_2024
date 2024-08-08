package Proyecto_EricesJava.JavaProyecto.Servicios;

import Proyecto_EricesJava.JavaProyecto.Entidades.Comprobante;
import Proyecto_EricesJava.JavaProyecto.Entidades.Carrito;
import Proyecto_EricesJava.JavaProyecto.Entidades.ProductoCarrito;
import Proyecto_EricesJava.JavaProyecto.Entidades.DetalleComprobante;
import Proyecto_EricesJava.JavaProyecto.Excepciones.ResourceNotFoundException;
import Proyecto_EricesJava.JavaProyecto.Repositorios.CarritoRepositorio;
import Proyecto_EricesJava.JavaProyecto.Repositorios.ComprobanteRepositorio;
import Proyecto_EricesJava.JavaProyecto.Repositorios.ProductoCarritoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComprobanteServicio {

    @Autowired
    private CarritoRepositorio carritoRepositorio;

    @Autowired
    private ProductoCarritoRepositorio productoCarritoRepositorio;

    @Autowired
    private ComprobanteRepositorio comprobanteRepositorio;

    public Comprobante generarComprobante(Long clienteId) {
        Carrito carrito = carritoRepositorio.findByClienteId(clienteId);
        List<ProductoCarrito> productosNoEntregados = carrito.getProductos().stream().filter(pc -> !pc.isDelivered()).collect(Collectors.toList());

        if (productosNoEntregados.isEmpty()) {
            throw new RuntimeException("No hay productos no entregados en el carrito.");
        }

        double total = productosNoEntregados.stream().mapToDouble(pc -> pc.getProducto().getPrecio() * pc.getCantidad()).sum();

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

