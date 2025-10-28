package com.example.transitweb.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entidad JPA para la tabla 'transacciones_monedero'.
 * Registra todas las transacciones de saldo (recargas y pagos de pasajes) del Pasajero.
 */
@Entity
@Table(name = "transacciones_monedero")
@Data
public class TransaccionMonedero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pasajero_id", nullable = false)
    private Usuario pasajero; // El usuario que realiza la transacción

    @Column(nullable = false)
    private Double monto;

    @Column(name = "tipo_transaccion", nullable = false)
    private String tipoTransaccion; // Ej: RECARGA, PAGO_VIAJE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id")
    private Bus bus; // El bus donde ocurrió el pago (si es PAGO_VIAJE)

    @Column(name = "fecha_hora_transaccion", nullable = false)
    private LocalDateTime fechaHoraTransaccion = LocalDateTime.now();

    @Column(name = "referencia_pago", unique = true)
    private String referenciaPago; // Código de transacción, QR leído, etc.
}

