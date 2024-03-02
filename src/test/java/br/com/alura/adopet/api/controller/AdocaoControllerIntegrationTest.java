package br.com.alura.adopet.api.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.com.alura.adopet.api.service.AdocaoService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdocaoControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    
    @MockBean
	private AdocaoService adocaoService;

    @Test
    void deveriaDevolverCodigo400ParaSolicitacaoDeAdocaoComErros() {
        // ARRANGE
        String json = "{}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // ACT
        ResponseEntity<Void> response = restTemplate.exchange(
                "/adocoes",
                HttpMethod.POST,
                new HttpEntity<>(json, headers),
                Void.class
        );

        // ASSERT
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deveriaDevolverCodigo200ParaSolicitacaoDeAdocaoSemErros() {
        // ARRANGE
        String json = """
                {
                    "idPet": 2,
                    "idTutor": 1,
                    "motivo": "Motivo qualquer"
                }
                """;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // ACT
        ResponseEntity<String> response = restTemplate.exchange(
                "/adocoes",
                HttpMethod.POST,
                new HttpEntity<>(json, headers),
                String.class
        );

        // ASSERT
        Assertions.assertTrue(response.getBody().contains("Adocação solicitada com sucesso!"));
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
