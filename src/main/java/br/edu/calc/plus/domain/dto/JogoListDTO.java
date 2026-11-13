package br.edu.calc.plus.domain.dto;

import java.time.LocalDateTime;

import br.edu.calc.plus.util.Util;
import groovy.transform.builder.Builder;

@Builder
public class JogoListDTO {

	private int idPartida;

	private LocalDateTime dataJogo;

	private double bonificacao;

	private int acertos, erros;
	
	private long tempo;

	public String getDataFormatada() {
		return Util.formatarData(dataJogo);
	}

    public JogoListDTO() {
    }

    public JogoListDTO(int idPartida, LocalDateTime dataJogo, double bonificacao, int acertos, int erros, long tempo) {
        this.idPartida = idPartida;
        this.dataJogo = dataJogo;
        this.bonificacao = bonificacao;
        this.acertos = acertos;
        this.erros = erros;
        this.tempo = tempo;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public LocalDateTime getDataJogo() {
        return dataJogo;
    }

    public void setDataJogo(LocalDateTime dataJogo) {
        this.dataJogo = dataJogo;
    }

    public double getBonificacao() {
        return bonificacao;
    }

    public void setBonificacao(double bonificacao) {
        this.bonificacao = bonificacao;
    }

    public int getAcertos() {
        return acertos;
    }

    public void setAcertos(int acertos) {
        this.acertos = acertos;
    }

    public int getErros() {
        return erros;
    }

    public void setErros(int erros) {
        this.erros = erros;
    }

    public long getTempo() {
        return tempo;
    }

    public void setTempo(long tempo) {
        this.tempo = tempo;
    }
}
