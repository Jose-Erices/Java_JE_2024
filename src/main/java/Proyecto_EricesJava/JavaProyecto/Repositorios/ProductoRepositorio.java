package Proyecto_EricesJava.JavaProyecto.Repositorios;


import Proyecto_EricesJava.JavaProyecto.Entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
}
