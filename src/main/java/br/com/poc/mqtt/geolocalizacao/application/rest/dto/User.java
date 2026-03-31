package br.com.poc.mqtt.geolocalizacao.application.rest.dto;

import lombok.Data;

@Data
public class User {

    private String nome;
    private Integer idade;
    private Double latitude;
    private Double longitude;
}
