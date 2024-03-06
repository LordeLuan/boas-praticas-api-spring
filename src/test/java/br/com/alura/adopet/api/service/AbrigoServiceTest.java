package br.com.alura.adopet.api.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.adopet.api.dto.CadastrarAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {
	
	@Mock
	private CadastrarAbrigoDto dto;
	
	private Abrigo abrigo;

	@Mock
	private AbrigoRepository abrigoRepository;
	
	@InjectMocks
	private AbrigoService abrigoService;
	
	@Mock
	private List<Abrigo> lista = new ArrayList<>();
	
	@Test
	void deveriaListarTodosOsAbrigos() {
		BDDMockito.given(abrigoRepository.findAll()).willReturn(lista);
		
		abrigoService.listarTodos();
	
		Assertions.assertEquals(0, lista.size());
	}
	
	@Test
	void deveriaCadastrarUmAbrigo() {
		BDDMockito.given(abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email())).willReturn(false);
		
		abrigo = new Abrigo("teste", "teste", "teste");
		
		abrigoService.cadastrar(dto);
		
		BDDMockito.then(abrigoRepository).should().save(abrigo);
	}
	
	@Test
	void deveriaLancarExcecaoAoCadastrarAbrigo() {
		BDDMockito.given(abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email())).willReturn(true);
		
		Assertions.assertThrows(ValidacaoException.class, () -> abrigoService.cadastrar(dto));
	}

}
