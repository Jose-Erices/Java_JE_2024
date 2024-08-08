package Proyecto_EricesJava.JavaProyecto.Controladores;

import Proyecto_EricesJava.JavaProyecto.Entidades.Comprobante;
import Proyecto_EricesJava.JavaProyecto.Servicios.ComprobanteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invoices")
public class ComprobanteControlador {

    @Autowired
    private ComprobanteServicio comprobanteServicio;

    @PostMapping("/{clienteId}")
    public ResponseEntity<Comprobante> generarComprobante(@PathVariable Long clienteId) {
        try {
            Comprobante comprobante = comprobanteServicio.generarComprobante(clienteId);
            return new ResponseEntity<>(comprobante, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<Comprobante> obtenerUltimoComprobante(@PathVariable Long clienteId) {
        try {
            Comprobante comprobante = comprobanteServicio.obtenerUltimoComprobante(clienteId);
            return new ResponseEntity<>(comprobante, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
