package Proyecto_EricesJava.JavaProyecto.Repositorios;

import Proyecto_EricesJava.JavaProyecto.Entidades.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepositorio extends JpaRepository<Carrito, Long> {
    Carrito findByClienteId(Long clienteId);
}
