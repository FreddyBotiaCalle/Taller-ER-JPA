package com.taller.erjpa.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DocenteRestIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void crearDocente_y_obtenerPorListaPaginada() {
        String base = "http://localhost:" + port + "/api/docentes";

        String nuevo = "{\"nombresDocente\":\"Test\",\"apellidosDocente\":\"User\",\"nombreGrupo\":\"G9\",\"correo\":\"test.user@correo.com\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(nuevo, headers);

        ResponseEntity<String> postResp = restTemplate.postForEntity(base, request, String.class);
        assertThat(postResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResp.getBody()).contains("correo");
        assertThat(postResp.getBody()).contains("test.user@correo.com");

        // now request a paged list with size=1
        String pagedUrl = base + "?page=0&size=1";
        ResponseEntity<String> pageResp = restTemplate.getForEntity(pagedUrl, String.class);
        assertThat(pageResp.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(pageResp.getBody()).contains("content");
    }
}
