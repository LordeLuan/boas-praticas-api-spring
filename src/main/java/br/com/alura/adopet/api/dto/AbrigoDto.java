package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Abrigo;

public record AbrigoDto(String nome, String telefone, String email) {

	public AbrigoDto(Abrigo a) {
		this(a.getNome(), a.getTelefone(), a.getEmail());
	}
}
