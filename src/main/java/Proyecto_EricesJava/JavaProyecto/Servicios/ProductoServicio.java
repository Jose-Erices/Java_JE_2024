package Proyecto_EricesJava.JavaProyecto.Servicios;



import Proyecto_EricesJava.JavaProyecto.Entidades.Producto;
import Proyecto_EricesJava.JavaProyecto.Repositorios.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServicio {
    @Autowired
    private ProductoRepositorio productoRepositorio;

    public Producto crearProducto(Producto producto) {
        return productoRepositorio.save(producto);
    }

    public List<Producto> obtenerProductos() {
        return productoRepositorio.findAll();
    }

    public Producto obtenerProductoPorId(Long id) {
        return productoRepositorio.findById(id).orElse(null);
    }

    public Producto actualizarProducto(Producto producto) {
        return productoRepositorio.save(producto);
    }

    public void eliminarProducto(Long id) {
        productoRepositorio.deleteById(id);
    }
}
