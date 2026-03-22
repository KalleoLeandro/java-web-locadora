package br.com.foursys.locadora.util;

/**
 * Enum com generos de jogos de videogame.
 */
public enum Genero {

	ACAO("Acao"),
	AVENTURA("Aventura"),
	BATTLE_ROYALE("Battle Royale"),
	CORRIDA("Corrida"),
	ESPORTE("Esporte"),
	ESTRATEGIA("Estrategia"),
	FPS("FPS"),
	LUTA("Luta"),
	MMORPG("MMORPG"),
	MOBA("MOBA"),
	MUNDO_ABERTO("Mundo Aberto"),
	PLATAFORMA("Plataforma"),
	PUZZLE("Puzzle"),
	RPG("RPG"),
	SANDBOX("Sandbox"),
	SIMULACAO("Simulacao"),
	SURVIVAL("Survival"),
	TERROR("Terror");

	private String descricao;

	Genero(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
