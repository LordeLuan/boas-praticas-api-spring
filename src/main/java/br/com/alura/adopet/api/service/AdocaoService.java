package br.com.alura.adopet.api.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validations.ValidacaoSolicitacaoAdocao;

@Service
public class AdocaoService {

	@Autowired
	private PetRepository petRepository;
	
	@Autowired
	private TutorRepository tutorRepository;
	
	@Autowired
	private AdocaoRepository repository;

	@Autowired
	private EmailService emailService;
	
//	O Spring busca todas as classes que implementam esta interface e inserem nesta lista
	@Autowired
	private List<ValidacaoSolicitacaoAdocao> validacoes;

	public void solicitar(SolicitacaoAdocaoDto dto) {
		Pet pet = petRepository.getReferenceById(dto.idPet());
		Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());

//		Chama todas as classes que implementam a interface para validar, 
//		sendo cada implementação de validação diferente uma da outra
		validacoes.forEach(x -> x.validar(dto));
		
//		Usando contrutor para encapsular getters e setter
		Adocao adocao = new Adocao(tutor, pet, dto.motivo());
		repository.save(adocao);

		String emailBody = "Olá " + adocao.getPet().getAbrigo().getNome()
				+ "Uma solicitação de adoção foi registrada hoje para o pet: " + adocao.getPet().getNome()
				+ ". \nFavor avaliar para aprovação ou reprovação.";
		emailService.enviarEmail(adocao.getTutor().getEmail(), "Adoção solicitada", emailBody);
	}

	public void aprovar(AprovacaoAdocaoDto dto) {
		Adocao adocao = repository.getReferenceById(dto.id());
		adocao.marcarComoAprovado();
		repository.save(adocao);

		String emailBody = "Parabéns " + adocao.getTutor().getNome() + "Sua adoção do pet " + adocao.getPet().getNome()
				+ ", solicitada em " + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
				+ ", foi aprovada.\nFavor entrar em contato com o abrigo " + adocao.getPet().getAbrigo().getNome()
				+ " para agendar a busca do seu pet.";
		emailService.enviarEmail(adocao.getTutor().getEmail(), "Adoção aprovada", emailBody);
	}

	public void reprovar(ReprovacaoAdocaoDto dto) {
		Adocao adocao = repository.getReferenceById(dto.id());
		adocao.marcarComoReprovado(dto.justificava());
		repository.save(adocao);

		String emailBody = "Olá " + adocao.getTutor().getNome() + "Infelizmente sua adoção do pet "
				+ adocao.getPet().getNome() + ", solicitada em "
				+ adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
				+ ", foi reprovada pelo abrigo " + adocao.getPet().getAbrigo().getNome()
				+ " com a seguinte justificativa: " + adocao.getJustificativaStatus();
		emailService.enviarEmail(adocao.getTutor().getEmail(), "Adoção reprovada", emailBody);
	}
}
