package com.example.transitweb.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad JPA para la tabla 'documentos_bus'.
 * Registra la documentación legal y administrativa de cada unidad vehicular (e.g., SOAT, Revisiones).
 */
@Entity
@Table(name = "documentos_bus")
@Data
public class DocumentoBus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con el Bus al que pertenece el documento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @Column(name = "tipo_documento", nullable = false, length = 50)
    private String tipoDocumento; // Ej: SOAT, Revisión Técnica, Permiso de Operación

    @Column(name = "numero_documento", unique = true, length = 100)
    private String numeroDocumento;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    // Campo CRÍTICO: fecha de expiración para alertas administrativas
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(name = "archivo_url", length = 255)
    private String archivoUrl; // URL o path al archivo digital (PDF/Imagen)

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();
}

