package br.com.alura.adopet.api.validations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;

// Adiciona a extensao na classe para que o mockito possa ler a classe e injetar os mocks
@ExtendWith(MockitoExtension.class)
class ValidacaoPetDisponivelTest {

// 	Mocka o repositorio para a utilizacao no metodo validar
	@Mock
	private PetRepository petRepository;

	@Mock
	private Pet pet;
	
	@Mock
	private SolicitacaoAdocaoDto dto;

//	Informa pro mockito que nesta injecao terao atributos de mocks dentro dela
	@InjectMocks
	private ValidacaoPetDisponivel validacao;

	@Test
	void deveriaPermitirSolicitacaoDeAdocaoPet() {
//		Quando a classe PetRepository chamar o metodo getReferenceById, será retornado um objeto tbm mockado
//		ARRANGE
		BDDMockito.given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
		
		BDDMockito.given(pet.getAdotado()).willReturn(false);
		
		// ACT
		// ASSERT
		Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
	}
	
	@Test
	void naoDeveriaPermitirSolicitacaoDeAdocaoPet() {
//		ARRANGE
//		Quando a classe PetRepository chamar o metodo getReferenceById, será retornado um objeto tbm mockado
		BDDMockito.given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);

		BDDMockito.given(pet.getAdotado()).willReturn(true);
		
		// ACT
		// ASSERT
		Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
	}

}
