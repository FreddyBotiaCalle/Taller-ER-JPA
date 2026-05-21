package com.taller.erjpa.integration;

import com.taller.erjpa.dto.CrearFormatoARequest;
import com.taller.erjpa.dto.FormatoADto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FormatoAIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void crearFormatoA_dev_returnsCreated() {
        java.util.List<String> objetivos = java.util.List.of("Analizar literatura","Diseñar prototipo","Validar resultados");

        CrearFormatoARequest request = new CrearFormatoARequest(
            1L,
            "TIA",
            "Titulo Test",
            "Objetivo general",
            objetivos
        );

        ResponseEntity<FormatoADto> response = restTemplate.postForEntity(
                "/api/formatos-a",
                request,
                FormatoADto.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().idFormA0()).isNotNull();
        assertThat(response.getBody().titulo()).isEqualTo("Titulo Test");
    }

    @Test
    void crearFormatoA_invalidObjetives_returnsBadRequest() {
        java.util.List<String> objetivos = java.util.List.of("Objetivo 1","Analizar datos");

        CrearFormatoARequest request = new CrearFormatoARequest(
            1L,
            "TIA",
            "Titulo Invalid Test",
            "Objetivo general",
            objetivos
        );

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/formatos-a",
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Each objective must start with an infinitive verb");
    }
}
