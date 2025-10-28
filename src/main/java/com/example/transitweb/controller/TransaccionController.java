package com.example.transitweb.controller;

import com.example.transitweb.dto.PagoPasajeRequestDTO;
import com.example.transitweb.dto.RecargaMonederoRequestDTO;
import com.example.transitweb.dto.PagoQrDTO;
import com.example.transitweb.entity.TransaccionMonedero;
import com.example.transitweb.service.TransaccionMonederoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionMonederoService transaccionService;

    @PostMapping("/recarga")
    public ResponseEntity<TransaccionMonedero> recargar(@RequestBody RecargaMonederoRequestDTO request) {
        TransaccionMonedero t = transaccionService.recargarMonedero(request);
        return new ResponseEntity<>(t, HttpStatus.CREATED);
    }

    @PostMapping("/pago-pasaje")
    public ResponseEntity<?> pagar(@RequestBody PagoPasajeRequestDTO request) {
        try {
            TransaccionMonedero t = transaccionService.pagarPasaje(request);
            return new ResponseEntity<>(t, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // QR para conductor: genera un payload que el pasajero escaneará para pagar
    // El frontend del conductor mostrará este payload como un código QR
    @GetMapping("/qr")
    public ResponseEntity<PagoQrDTO> generarQr(@RequestParam Long asignacionId,
                                               @RequestParam Long tarifaId) {
        // Payload simple JSON; el frontend generará el código QR visual
        String payload = String.format("{\"asignacionId\":%d,\"tarifaId\":%d}", asignacionId, tarifaId);
        return ResponseEntity.ok(new PagoQrDTO(payload));
    }
}


