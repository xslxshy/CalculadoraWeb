package br.edu.calc.plus.domain.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import br.edu.calc.plus.domain.Usuario;
import groovy.transform.builder.Builder;

@Builder
public class UserDTO {

	@NotNull
	@NotEmpty
	private String nome, login, senha,email;
	private String cidade;
	
	@Past
	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
	private LocalDate nascimento;

	public Usuario ConvertUsuario() {
		return new Usuario(null, nome, login, email, senha, cidade, nascimento);
	}

    public UserDTO() {
    }

    public UserDTO(String nome, String login, String senha, String email, String cidade, LocalDate nascimento) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.cidade = cidade;
        this.nascimento = nascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }
}
