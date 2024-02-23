package br.com.alura.adopet.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.adopet.api.dto.AtualizarTutorDto;
import br.com.alura.adopet.api.dto.CadastrarTutorDto;
import br.com.alura.adopet.api.service.TutorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tutores")
public class TutorController {

	@Autowired
	private TutorService tutorService;

	@PostMapping
	@Transactional
	public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastrarTutorDto tutor) {
		try {
			tutorService.cadastrar(tutor);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Dados já cadastrados para outro tutor!");
		}
	}

	@PutMapping
	@Transactional
	public ResponseEntity<String> atualizar(@RequestBody @Valid AtualizarTutorDto tutor) {
		try {
			tutorService.atualizar(tutor);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

}
