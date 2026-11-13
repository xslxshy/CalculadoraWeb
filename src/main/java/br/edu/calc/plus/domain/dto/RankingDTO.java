package br.edu.calc.plus.domain.dto;


//@Builder
public class RankingDTO {

	private Integer idUser;
	
	private String nome;
	
	private double bonusTotal;
	
	private long competicoes;
	
	private long tempoTotal;

    public RankingDTO() {
    }

    public RankingDTO(Integer idUser, String nome, double bonusTotal, long competicoes, long tempoTotal) {
        this.idUser = idUser;
        this.nome = nome;
        this.bonusTotal = bonusTotal;
        this.competicoes = competicoes;
        this.tempoTotal = tempoTotal;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getBonusTotal() {
        return bonusTotal;
    }

    public void setBonusTotal(double bonusTotal) {
        this.bonusTotal = bonusTotal;
    }

    public long getCompeticoes() {
        return competicoes;
    }

    public void setCompeticoes(long competicoes) {
        this.competicoes = competicoes;
    }

    public long getTempoTotal() {
        return tempoTotal;
    }

    public void setTempoTotal(long tempoTotal) {
        this.tempoTotal = tempoTotal;
    }
}

