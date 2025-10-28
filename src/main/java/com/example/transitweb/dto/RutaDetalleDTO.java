package com.example.transitweb.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class RutaDetalleDTO extends RutaDTO {
    private List<ParaderoDTO> paraderos;
}