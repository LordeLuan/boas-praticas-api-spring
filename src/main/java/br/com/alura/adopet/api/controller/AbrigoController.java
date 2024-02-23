package br.com.alura.adopet.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.adopet.api.dto.AbrigoDto;
import br.com.alura.adopet.api.dto.CadastrarAbrigoDto;
import br.com.alura.adopet.api.dto.CadastrarPetDto;
import br.com.alura.adopet.api.dto.DadosDetalhesPet;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/abrigos")
public class AbrigoController {

	@Autowired
	private AbrigoService service;

	@Autowired
	private PetService petService;
	
	@GetMapping
	public ResponseEntity<List<AbrigoDto>> listar() {
		return ResponseEntity.ok(service.listarTodos());
	}

	@PostMapping
	@Transactional
	public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastrarAbrigoDto dto) {
		try {
			service.cadastrar(dto);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/{idOuNome}/pets")
	public ResponseEntity<List<DadosDetalhesPet>> listarPets(@PathVariable String idOuNome) {
		try {
			return ResponseEntity.ok(service.listarPets(idOuNome));
		} catch (EntityNotFoundException enfe) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/{idOuNome}/pets")
	@Transactional
	public ResponseEntity<String> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid CadastrarPetDto pet) {
		try {
			Abrigo abrigo = service.carregaAbrigo(idOuNome);
			petService.cadastrarPet(abrigo, pet);
			return ResponseEntity.ok().build();   
		} catch (EntityNotFoundException enfe) {
			return ResponseEntity.notFound().build();
		}
	}

}
