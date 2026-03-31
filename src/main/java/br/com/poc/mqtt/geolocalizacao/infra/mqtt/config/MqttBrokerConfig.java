package br.com.poc.mqtt.geolocalizacao.infra.mqtt.config;

import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@EnableIntegration
@RequiredArgsConstructor
@Configuration
public class MqttBrokerConfig {

    private final MqttBrokerProperties mqttBrokerProperties;

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {

        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();

        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{mqttBrokerProperties.getHost()});
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        options.setMaxInflight(100);
        options.setKeepAliveInterval(20);

        factory.setConnectionOptions(options);
        return factory;
    }

    //Producer
    @Bean
    public MessageChannel mqttProducerChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttProducerChannel")
    public MessageHandler mqttProducer() {

        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(
                mqttBrokerProperties.getClientId(),
                mqttClientFactory()
        );

        handler.setAsync(true);
        handler.setDefaultQos(0);
        handler.setDefaultTopic(mqttBrokerProperties.getProducerTopic());

        return handler;
    }


}