package br.com.poc.mqtt.geolocalizacao.application.rest;

import br.com.poc.mqtt.geolocalizacao.application.rest.dto.User;
import br.com.poc.mqtt.geolocalizacao.infra.mqtt.UserMqttProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/geolocalizacao")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserMqttProducer userMqttProducer;

    @PostMapping
    public ResponseEntity<?> receberGeolocalizacao(@RequestBody User user) {
        log.info("[UserController - receberGeolocalizacao] - Request recebida: {}", user);
        try {
            userMqttProducer.sendMessage(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
