package br.com.alura.adopet.api.validations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;

@Component
public class ValidacaoTutorComAdocaoEmAndamento implements ValidacaoSolicitacaoAdocao {
	
	@Autowired
	private TutorRepository tutorRepository;
	
	@Autowired
	private AdocaoRepository adocaoRepository;
	
	@Override
	public void validar(SolicitacaoAdocaoDto dto) {
		Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());
		
		List<Adocao> adocoes = adocaoRepository.findAll();
		for (Adocao a : adocoes) {
			int contador = 0;
			if (a.getTutor() == tutor && a.getStatus() == StatusAdocao.APROVADO) {
				contador = contador + 1;
			}
			if (contador == 5) {
				throw new ValidacaoException("Tutor chegou ao limite máximo de 5 adoções!");
			}
		}
	
	}
}
