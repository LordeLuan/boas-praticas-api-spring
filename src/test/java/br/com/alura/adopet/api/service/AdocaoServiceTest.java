package br.com.alura.adopet.api.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validations.ValidacaoSolicitacaoAdocao;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

	@Mock
	private PetRepository petRepository;

	@Mock
	private TutorRepository tutorRepository;

	@Mock
	private AdocaoRepository repository;

	@Mock
	private EmailService emailService;

	// Instancia o objeto mas é controlavel as ações do objeto
	@Spy
	private List<ValidacaoSolicitacaoAdocao> validacoes = new ArrayList<>();
	
	@Mock
	private ValidacaoSolicitacaoAdocao validador1;

	@Mock
	private ValidacaoSolicitacaoAdocao validador2;

	@Mock
	private Pet pet;

	@Mock
	private Tutor tutor;

	@Mock
	private Abrigo abrigo;

	private SolicitacaoAdocaoDto dto;

	@InjectMocks
	private AdocaoService adocaoService;

//	Captura o argumento passado como parametro para algum obj mockado
	@Captor
	private ArgumentCaptor<Adocao> adocaoCaptor;

	@Test
	void deveriaSalvarAdocaoAoSolicitar() {
//		ARRANGE
		this.dto = new SolicitacaoAdocaoDto(10l, 20l, "Qualquer");

		BDDMockito.given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
		BDDMockito.given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
		BDDMockito.given(pet.getAbrigo()).willReturn(abrigo);
		
		
//		ACT
		adocaoService.solicitar(dto);
		
//		ASSERT
//		Valida se o metovo save da classe AdocaoRepository foi chamado
		BDDMockito.then(repository).should().save(adocaoCaptor.capture());
//		Captura o valor passado para o metodo "salvar" dentro do metodo "adocaoService.solicitar"
		Adocao adocaoSalva = adocaoCaptor.getValue();

		Assertions.assertEquals(pet, adocaoSalva.getPet());
		Assertions.assertEquals(tutor, adocaoSalva.getTutor());
		Assertions.assertEquals(dto.motivo(), adocaoSalva.getMotivo());
	}
	
	@Test
	void deveriaChamarValidadorDeAdocaoAoSolicitar() {
//		ARRANGE
		this.dto = new SolicitacaoAdocaoDto(10l, 20l, "Qualquer");

		BDDMockito.given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
		BDDMockito.given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
		BDDMockito.given(pet.getAbrigo()).willReturn(abrigo);
		
		validacoes.add(validador1);
		validacoes.add(validador2);
		
//		ACT
		adocaoService.solicitar(dto);
		
//		ASSERT
		//Valida se os metodos da lista de validacao foram chamados
		BDDMockito.then(validador1).should().validar(dto);
		BDDMockito.then(validador2).should().validar(dto);
	
	}

}
