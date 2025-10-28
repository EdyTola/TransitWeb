package com.example.transitweb.dto;

import lombok.Data;

@Data
public class TelemetriaMessageDTO {
    private Long busId;
    private Double lat;
    private Double lng;
    private Double vel;
    private Integer ocupacion;
}


