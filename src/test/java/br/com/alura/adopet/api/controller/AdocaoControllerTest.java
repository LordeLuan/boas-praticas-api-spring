package br.com.alura.adopet.api.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.alura.adopet.api.service.AdocaoService;

@SpringBootTest
@AutoConfigureMockMvc
class AdocaoControllerTest {
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private AdocaoService adocaoService;
	
	@Test
	void deveriaRetornarCodigo400ParaSolicitacaoDeAdocaoComErro() throws Exception {
		// 	ARRANGE
		String json = "{}";
		
		//	ACT
		// Simula uma requisição ao edpoint especificado e passando os dados no body e pega a resposta da requisição
		var response = mvc.perform(
				MockMvcRequestBuilders.post("/adocoes")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON)
				).andReturn().getResponse();
		
		//	ACERT
		Assertions.assertEquals(400, response.getStatus());
	}
	
	@Test
	void deveriaRetornarCodigo200ParaSolicitacaoDeAdocaoSemErros() throws Exception {
		// 	ARRANGE
		String json = """
						{
						    "idPet": 1,
						    "idTutor": 1,
						    "motivo": "Motivo qualquer"
						}
					""";
		
		//	ACT
		// Simula uma requisição ao edpoint especificado e passando os dados no body e pega a resposta da requisição
		var response = mvc.perform(
				MockMvcRequestBuilders.post("/adocoes")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON)
				).andReturn().getResponse();
		
		//	ACERT
		Assertions.assertEquals(200, response.getStatus());
	}

}
