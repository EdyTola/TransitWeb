package com.example.transitweb.service;

import com.example.transitweb.dto.ReporteEventoRequestDTO;
import com.example.transitweb.entity.ReporteEvento;
import com.example.transitweb.entity.Usuario;
import com.example.transitweb.repository.ReporteEventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Servicio para manejar la creación y gestión de Reportes de Eventos (Incidencias).
 */
@Service
public class ReporteEventoService {

    @Autowired
    private ReporteEventoRepository reporteEventoRepository;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Crea un nuevo reporte de evento (incidencia) basado en la solicitud del usuario.
     * * @param request DTO con los detalles del reporte.
     * @return El ReporteEvento recién creado.
     */
    @Transactional
    public ReporteEvento crearReporte(ReporteEventoRequestDTO request) {

        // 1. Obtener el usuario que reporta
        Usuario usuarioReporta = usuarioService.getUsuarioById(request.getUsuarioId());

        // 2. Crear y guardar el ReporteEvento
        ReporteEvento reporte = new ReporteEvento();
        reporte.setUsuarioReporta(usuarioReporta);
        reporte.setTipo(request.getTipo());
        reporte.setDescripcion(request.getDescripcion());
        reporte.setLatitud(request.getLatitud());
        reporte.setLongitud(request.getLongitud());
        reporte.setFechaHoraReporte(LocalDateTime.now());
        reporte.setEstado("ABIERTO"); // Estado inicial

        return reporteEventoRepository.save(reporte);
    }

    /**
     * Método usado por el Administrador para obtener todos los reportes activos/abiertos.
     */
    @Transactional(readOnly = true)
    public List<ReporteEvento> getReportesAbiertos() {
        return reporteEventoRepository.findByEstado("ABIERTO");
    }

    /**
     * Método usado por el Administrador para cambiar el estado de un reporte.
     */
    @Transactional
    public ReporteEvento actualizarEstadoReporte(Long reporteId, String nuevoEstado) {
        ReporteEvento reporte = reporteEventoRepository.findById(reporteId)
                .orElseThrow(() -> new NoSuchElementException("Reporte con ID " + reporteId + " no encontrado."));

        // Lógica de validación de estados (opcional, pero buena práctica)
        if (!List.of("ABIERTO", "EN ATENCION", "RESUELTO", "CERRADO").contains(nuevoEstado.toUpperCase())) {
            throw new IllegalArgumentException("Estado de reporte inválido: " + nuevoEstado);
        }

        reporte.setEstado(nuevoEstado.toUpperCase());
        return reporteEventoRepository.save(reporte);
    }

}