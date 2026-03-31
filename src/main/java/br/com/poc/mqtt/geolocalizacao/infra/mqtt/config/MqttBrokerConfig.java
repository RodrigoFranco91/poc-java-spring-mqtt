package br.com.poc.mqtt.geolocalizacao.infra.mqtt.config;

import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class MqttBrokerConfig {

    private final MqttBrokerProperties mqttBrokerProperties;

    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{mqttBrokerProperties.getHost()});
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        options.setMaxInflight(100);
        options.setKeepAliveInterval(20);
        return options;
    }

    @Bean
    public IMqttClient mqttClient() throws MqttException {
        IMqttClient client = new MqttClient(
                mqttBrokerProperties.getHost(),
                mqttBrokerProperties.getClientId()
        );
        client.connect(mqttConnectOptions());
        return client;
    }


}
