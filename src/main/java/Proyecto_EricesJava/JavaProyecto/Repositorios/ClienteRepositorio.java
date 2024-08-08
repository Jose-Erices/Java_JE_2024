package Proyecto_EricesJava.JavaProyecto.Repositorios;

import Proyecto_EricesJava.JavaProyecto.Entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
}
