package br.com.alura.adopet.api.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "abrigos")
public class Abrigo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	private String nome;

	private String telefone;

	private String email;

	@OneToMany(mappedBy = "abrigo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Pet> pets;

	public Abrigo() {
		super();
	}

	public Abrigo(String nome, String telefone, String email) {
		super();
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Abrigo abrigo = (Abrigo) o;
		return Objects.equals(id, abrigo.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}
}
