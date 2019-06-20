package it.polito.tdp.formulaone.model;

import java.util.HashMap;
import java.util.Map;

public class Pilota {
	
	private int id;
	private Map<Integer, Integer> mappa = new HashMap<Integer, Integer>();
	private int giro;
	private int tempoGara = 0;
	private int punti;
	
	public Pilota(int id) {
		super();
		this.id = id;
	}
	
	public int getTempoGara() {
		return tempoGara;
	}

	public void setTempoGara(int tempoGara) {
		this.tempoGara += tempoGara;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Map<Integer, Integer> getMappa() {
		return mappa;
	}
	public void setMappa(Map<Integer, Integer> mappa) {
		this.mappa = mappa;
	}
	public int getGiro() {
		return giro;
	}
	public void setGiro(int giro) {
		this.giro += giro;
	}

	@Override
	public String toString() {
		return String.format("Pilota [id=%s, mappa=%s, giro=%s]", id, mappa, giro);
	}

	public int getPunti() {
		return punti;
	}

	public void addPunti(int punti) {
		this.punti = punti++;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pilota other = (Pilota) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
