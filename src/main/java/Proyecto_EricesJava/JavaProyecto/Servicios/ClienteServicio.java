package Proyecto_EricesJava.JavaProyecto.Servicios;


import Proyecto_EricesJava.JavaProyecto.Entidades.Cliente;
import Proyecto_EricesJava.JavaProyecto.Repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServicio {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public Cliente crearCliente(Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }

    public List<Cliente> obtenerClientes() {
        return clienteRepositorio.findAll();
    }

    public Cliente obtenerClientePorId(Long id) {
        return clienteRepositorio.findById(id).orElse(null);
    }

    public Cliente actualizarCliente(Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }

    public void eliminarCliente(Long id) {
        clienteRepositorio.deleteById(id);
    }
}
