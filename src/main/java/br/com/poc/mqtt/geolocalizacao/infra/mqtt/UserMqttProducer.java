package br.com.poc.mqtt.geolocalizacao.infra.mqtt;

import br.com.poc.mqtt.geolocalizacao.application.rest.dto.User;
import br.com.poc.mqtt.geolocalizacao.infra.mqtt.config.MqttBrokerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserMqttProducer {

    private final MessageChannel mqttProducerChannel;
    private final MqttBrokerProperties mqttBrokerProperties;
    private final JsonMapper jsonMapper;

    public void sendMessage(User user) {
        log.info("Sending message: {}", user);

        String payload = jsonMapper.writeValueAsString(user);

        //POSTAGEM EM TÓPICO FIXO DEFINO EM MqttBrokerConfig
//        boolean send = mqttProducerChannel.send(
//                MessageBuilder.withPayload(payload).build()
//        );


        //DEIXANDO NOME DO TOPICO DINAMICO
        boolean send = mqttProducerChannel.send(
                MessageBuilder.withPayload(payload)
                        .setHeader(MqttHeaders.TOPIC, mqttBrokerProperties.getProducerTopic() + "/" + user.getNome())
                        .build()
        );


        if (!send) {
            log.error("Failed to send message {}", user);
        }
    }
}
