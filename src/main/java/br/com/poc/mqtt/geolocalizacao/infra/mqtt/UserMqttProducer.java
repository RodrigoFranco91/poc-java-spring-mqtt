package br.com.poc.mqtt.geolocalizacao.infra.mqtt;

import br.com.poc.mqtt.geolocalizacao.application.rest.dto.User;
import br.com.poc.mqtt.geolocalizacao.infra.mqtt.config.MqttBrokerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserMqttProducer {

    private final IMqttClient mqttClient;
    private final JsonMapper jsonMapper;
    private final MqttBrokerProperties mqttBrokerProperties;

    public void sendMessage(User user) throws Exception {
        log.info("Sending message: {}", user);

        String payload = jsonMapper.writeValueAsString(user);
        String topic = mqttBrokerProperties.getProducerTopic() + "/" + user.getNome();

        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(0);

        mqttClient.publish(topic, message);
    }
}
