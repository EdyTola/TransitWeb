package com.example.transitweb.dto;

import lombok.Data;

import java.util.List;

@Data
public class RutaDetalleDTO extends RutaDTO {
    private List<ParaderoDTO> paraderos;
}