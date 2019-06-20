package it.polito.tdp.formulaone.model;

public class Evento implements Comparable<Evento> {
	
	public enum Tipo {
		TRAGUARDO
	}
	
	//come modello l'evento
	private Pilota pilota;
	private int tempoTotale;
	private int giro;
	private Tipo tipo;
	
	@Override
	public int compareTo(Evento e) {
		
		return this.tempoTotale - e.tempoTotale;
	}

	public Evento(Pilota pilota, int tempoTotale, int giro, Tipo tipo) {
		super();
		this.pilota = pilota;
		this.tempoTotale = tempoTotale;
		this.giro = giro;
		this.tipo = tipo;
	}

	public Pilota getPilota() {
		return pilota;
	}

	public void setPilota(Pilota pilota) {
		this.pilota = pilota;
	}

	public int getTempoTotale() {
		return tempoTotale;
	}

	public void setTempoTotale(int tempoTotale) {
		this.tempoTotale = tempoTotale;
	}

	public int getGiro() {
		return giro;
	}

	public void setGiro(int giro) {
		this.giro = giro;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return String.format("Evento [pilota=%s, tempoTotale=%s, giro=%s, tipo=%s]", pilota, tempoTotale, giro, tipo);
	}
	

}