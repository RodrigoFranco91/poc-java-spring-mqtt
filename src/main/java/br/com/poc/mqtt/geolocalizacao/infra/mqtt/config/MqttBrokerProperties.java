package br.com.poc.mqtt.geolocalizacao.infra.mqtt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app.mqtt")
@Data
@Component
public class MqttBrokerProperties {

    private String host;
    private String clientId;
    private String producerTopic;

}