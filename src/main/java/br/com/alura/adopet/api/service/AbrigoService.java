package br.com.alura.adopet.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.adopet.api.dto.AbrigoDto;
import br.com.alura.adopet.api.dto.CadastrarAbrigoDto;
import br.com.alura.adopet.api.dto.DadosDetalhesPet;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;

@Service
public class AbrigoService {

	@Autowired
	private AbrigoRepository abrigoRepository;

	public List<AbrigoDto> listarTodos() {
		var lista = abrigoRepository.findAll();
		return lista.stream().map(a -> new AbrigoDto(a)).collect(Collectors.toList());
	}

	public void cadastrar(CadastrarAbrigoDto dto) {
		boolean jaCadastrado = abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email());

		if (jaCadastrado) {
			throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
		}

		Abrigo abrigo = new Abrigo(dto.nome(), dto.telefone(), dto.email());
		abrigoRepository.save(abrigo);
	}

	public List<DadosDetalhesPet> listarPets(String idOuNome) {
		Abrigo abrigo = carregaAbrigo(idOuNome);

		return abrigo.getPets().stream().map(a -> new DadosDetalhesPet(a)).toList();
	}
	
	public Abrigo carregaAbrigo(String idOuNome) {
		try {
			Long id = Long.parseLong(idOuNome);
			return abrigoRepository.getReferenceById(id);
		} catch (NumberFormatException enfe) {
			return abrigoRepository.findByNome(idOuNome);
		}
	}

}
