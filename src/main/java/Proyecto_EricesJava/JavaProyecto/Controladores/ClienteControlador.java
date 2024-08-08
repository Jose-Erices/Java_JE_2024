package Proyecto_EricesJava.JavaProyecto.Controladores;

import Proyecto_EricesJava.JavaProyecto.Entidades.Cliente;
import Proyecto_EricesJava.JavaProyecto.Servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Cliente", description = "Cliente Management System")
public class ClienteControlador {

    @Autowired
    private ClienteServicio clienteServicio;

    @Operation(summary = "Register a new client")
    @PostMapping("/register")
    public ResponseEntity<Cliente> registrarCliente(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteServicio.crearCliente(cliente);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    @Operation(summary = "Update client details")
    @PutMapping("/me")
    public ResponseEntity<Cliente> actualizarCliente(@RequestBody Cliente cliente) {
        Cliente clienteActualizado = clienteServicio.actualizarCliente(cliente);
        return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
    }

    @Operation(summary = "Get client by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Long id) {
        Cliente cliente = clienteServicio.obtenerClientePorId(id);
        return cliente != null ? new ResponseEntity<>(cliente, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
