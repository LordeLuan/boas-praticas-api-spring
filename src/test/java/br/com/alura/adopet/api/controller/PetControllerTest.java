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

import br.com.alura.adopet.api.service.PetService;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PetService petService;
	
	@Test
	void deveriaRetornarCodigo200ParaListagemDePets() throws Exception {
		//	ACT
		// Simula uma requisição GET ao edpoint especificado
		var response = mvc.perform(
				MockMvcRequestBuilders.get("/pets")
					.contentType(MediaType.APPLICATION_JSON)
				).andReturn().getResponse();
		
		//	ACERT
		Assertions.assertEquals(200, response.getStatus());
		Assertions.assertEquals("[]", response.getContentAsString());
	}

}
