package br.com.alura.adopet.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.adopet.api.dto.CadastrarPetDto;
import br.com.alura.adopet.api.dto.DadosDetalhesPet;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;

@Service
public class PetService {

	@Autowired
	private PetRepository petRepository;

	public List<DadosDetalhesPet> listarTodosDisponiveis() {
		var listaDisponiveis = petRepository.findAllByAdotadoFalse();
		var disponiveis = listaDisponiveis.stream().map(p -> new DadosDetalhesPet(p)).toList();
		return disponiveis;
	}

	public void cadastrarPet(Abrigo abrigo, CadastrarPetDto dto) {
		Pet pet = new Pet(dto);
		pet.setAbrigo(abrigo);
		pet.setAdotado(false);
		petRepository.save(pet);
	}
}
