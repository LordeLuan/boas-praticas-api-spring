package br.com.alura.adopet.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.adopet.api.dto.AtualizarTutorDto;
import br.com.alura.adopet.api.dto.CadastrarTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;

@Service
public class TutorService {

	@Autowired
	private TutorRepository repository;

	public void cadastrar(CadastrarTutorDto dto) {
		boolean telefoneOuEmailJaCadastrado = repository.existsByTelefoneOrEmail(dto.telefone(), dto.email());
		
		if (telefoneOuEmailJaCadastrado) {
			throw new ValidacaoException("Dados já cadastrados para outro tutor!");
		}
		Tutor tutor = new Tutor();
		tutor.setEmail(dto.email());
		tutor.setNome(dto.nome());
		tutor.setTelefone(dto.telefone());
		repository.save(tutor);
	}

	public void atualizar(AtualizarTutorDto dto) {
		Tutor tutor = repository.getReferenceById(dto.idTutor());
		tutor.atualizarDados(dto);
		repository.save(tutor);
	}
}
