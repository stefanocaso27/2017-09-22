package it.polito.tdp.formulaone.model;

public class TestModel {

	public static void main(String[] args) {

		Model m = new Model();
		
		//m.creaGrafo(2010);
		
		//m.calcolaBest(2010);
		
		Simulatore s = new Simulatore(0, 0);
		s.init(1);
		s.run();
	}

}
