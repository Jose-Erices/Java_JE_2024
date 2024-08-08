package Proyecto_EricesJava.JavaProyecto.Repositorios;

import Proyecto_EricesJava.JavaProyecto.Entidades.Comprobante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComprobanteRepositorio extends JpaRepository<Comprobante, Long> {
    Comprobante findTopByClienteIdOrderByFechaDesc(Long clienteId);
}
