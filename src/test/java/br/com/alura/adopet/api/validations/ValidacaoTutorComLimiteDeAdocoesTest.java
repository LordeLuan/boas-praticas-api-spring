package br.com.alura.adopet.api.validations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComLimiteDeAdocoesTest {
	
    @InjectMocks
    private ValidacaoTutorComLimiteAdocao validador;

    @Mock
    private AdocaoRepository adocaoRepository;
    
    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private SolicitacaoAdocaoDto dto;
    
    @Mock
    private Adocao adocao;
    
    @Mock
    private Tutor tutor;


    @Test
    void naoDeveriaPermitirSolicitacaoDeAdocaoTutorComAdocaoPendente() {
		// Arrange
		var lista = new ArrayList<Adocao>();
		lista.add(adocao);

		given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
		given(adocaoRepository.findAll()).willReturn(lista);
		given(adocao.getTutor()).willReturn(tutor);
		given(adocao.getStatus()).willReturn(StatusAdocao.AGUARDANDO_AVALIACAO);

		// Act + Assert
		assertThrows(ValidacaoException.class, () -> validador.validar(dto));
    }

	@Test
	void deveriaPermitirSolicitacaoDeAdocaoTutorSemSolicitacaoPendente() {
		// Arrange
		var lista = new ArrayList<Adocao>();
		lista.add(adocao);

		given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
		given(adocaoRepository.findAll()).willReturn(lista);
		given(adocao.getTutor()).willReturn(tutor);
		given(adocao.getStatus()).willReturn(StatusAdocao.APROVADO);

		// Act + Assert
		assertDoesNotThrow(() -> validador.validar(dto));
	}

}