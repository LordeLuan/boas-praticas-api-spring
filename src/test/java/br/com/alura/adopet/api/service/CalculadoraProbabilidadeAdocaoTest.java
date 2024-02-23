package br.com.alura.adopet.api.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.alura.adopet.api.dto.CadastrarPetDto;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.ProbabilidadeAdocao;
import br.com.alura.adopet.api.model.TipoPet;

class CalculadoraProbabilidadeAdocaoTest {

	@Test
	void petComIdadoBaixaEPesoBaixo() {
	    //idade 4 anos e 4kg - ALTA
		
//		ARRANGE / ARRUMANDO
	    Pet pet = new Pet(new CadastrarPetDto(
	            TipoPet.GATO,
	            "Miau",
	            "Siames",
	            4,
	            "Cinza",
	            4.0f
	    ));

	    CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
	    
//	    ACT / EXECUTA
	    ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

//	    ASSERT / VALLIDA O RETORNO
	    Assertions.assertEquals(ProbabilidadeAdocao.ALTA, probabilidade);
	}
	
	@DisplayName("Descrição do teste")
	@Test
	void petComIdadoAltaEPesoBaixo() {
		//idade 15 anos e 4kg - MEDIA
		
		Pet pet = new Pet(new CadastrarPetDto(
				TipoPet.GATO,
				"Miau",
				"Siames",
				15,
				"Cinza",
				4.0f
				));
		
		CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
		ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);
		
		Assertions.assertEquals(ProbabilidadeAdocao.MEDIA, probabilidade);
	}


}
