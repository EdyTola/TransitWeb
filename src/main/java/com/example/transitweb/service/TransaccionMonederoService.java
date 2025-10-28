package com.example.transitweb.service;

import com.example.transitweb.dto.PagoPasajeRequestDTO;
import com.example.transitweb.dto.RecargaMonederoRequestDTO;
import com.example.transitweb.entity.AsignacionBus;
import com.example.transitweb.entity.Tarifa;
import com.example.transitweb.entity.TransaccionMonedero;
import com.example.transitweb.entity.Usuario;
import com.example.transitweb.repository.AsignacionBusRepository;
import com.example.transitweb.repository.TarifaRepository;
import com.example.transitweb.repository.TransaccionMonederoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Servicio para manejar la lógica de negocio de Transacciones (Recargas y Pagos).
 * Depende de UsuarioService para actualizar el saldo.
 */
@Service
public class TransaccionMonederoService {

    @Autowired
    private TransaccionMonederoRepository transaccionMonederoRepository;

    @Autowired
    private TarifaRepository tarifaRepository;

    @Autowired
    private AsignacionBusRepository asignacionBusRepository;

    // Inyectamos el servicio que maneja la actualización del saldo del Usuario
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Procesa la recarga de saldo al monedero de un pasajero.
     * * @param request DTO con el ID del pasajero y el monto a recargar.
     * @return TransaccionMonedero recién creada.
     */
    @Transactional
    public TransaccionMonedero recargarMonedero(RecargaMonederoRequestDTO request) {

        // 1. Aumentar el saldo del usuario (Lógica en UsuarioService)
        Usuario pasajero = usuarioService.actualizarSaldo(request.getPasajeroId(), request.getMonto());

        // 2. Registrar la transacción
        TransaccionMonedero transaccion = new TransaccionMonedero();
        transaccion.setPasajero(pasajero);
        transaccion.setMonto(request.getMonto());
        transaccion.setTipoTransaccion("RECARGA");
        transaccion.setFechaHoraTransaccion(LocalDateTime.now());
        transaccion.setReferenciaPago("REC-" + UUID.randomUUID().toString().substring(0, 8)); // Generar referencia

        return transaccionMonederoRepository.save(transaccion);
    }

    /**
     * Procesa el pago de un pasaje, descontando el saldo del pasajero y registrando el ingreso.
     * * @param request DTO con el ID del pasajero, la tarifa aplicada y el turno activo.
     * @return TransaccionMonedero recién creada.
     */
    @Transactional
    public TransaccionMonedero pagarPasaje(PagoPasajeRequestDTO request) {

        Tarifa tarifa = tarifaRepository.findById(request.getTarifaId())
                .orElseThrow(() -> new NoSuchElementException("Tarifa no encontrada."));

        AsignacionBus asignacion = asignacionBusRepository.findById(request.getAsignacionId())
                .orElseThrow(() -> new NoSuchElementException("Asignación de bus no encontrada."));

        // El monto a descontar es el monto de la tarifa (negativo para descontar)
        double montoDescontar = tarifa.getMonto() * -1.0;

        // 1. Descontar el saldo del usuario (Lógica en UsuarioService)
        // Lanza IllegalArgumentException si el saldo es insuficiente
        Usuario pasajero = usuarioService.actualizarSaldo(request.getPasajeroId(), montoDescontar);

        // 2. Registrar la transacción (el ingreso)
        TransaccionMonedero transaccion = new TransaccionMonedero();
        transaccion.setPasajero(pasajero);
        transaccion.setBus(asignacion.getBus()); // Asociar al bus que generó el ingreso
        transaccion.setMonto(tarifa.getMonto()); // Registrar el monto (positivo para el ingreso)
        transaccion.setTipoTransaccion("PAGO_VIAJE");
        transaccion.setFechaHoraTransaccion(LocalDateTime.now());
        transaccion.setReferenciaPago("VIAJE-" + UUID.randomUUID().toString().substring(0, 8));

        return transaccionMonederoRepository.save(transaccion);
    }
}