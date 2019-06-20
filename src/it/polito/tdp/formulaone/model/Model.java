package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	
	private List<Race> gare;
	private Map<Integer, Race> idMap;
	private FormulaOneDAO dao;
	private SimpleWeightedGraph<Race, DefaultWeightedEdge> grafo;
	private List<Peso> archi;
	
	private double getPesoArco(int anno, Race r1, Race r2) {
		this.archi = dao.getPesiArchi(anno, idMap);
		double p = 0;
		
		for(Peso peso : archi) {
			if(peso.getR1().equals(r1) && peso.getR2().equals(r2)) {
				p = peso.getPeso();
			}
		}
		return p;	
	}

	public Model() {
		this.gare = new ArrayList<Race>();
		this.idMap = new HashMap<Integer, Race>();
		this.dao = new FormulaOneDAO();
	}
	
	public FormulaOneDAO getDao() {
		return dao;
	}

	public void creaGrafo(int anno) {
		dao.getAllRaces(idMap);
		this.grafo = new SimpleWeightedGraph<Race, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		gare = dao.getGareAnno(anno, idMap);
		this.archi = dao.getPesiArchi(anno, idMap);
		
		Graphs.addAllVertices(grafo, gare);
		
		for(Race r1 : grafo.vertexSet()) {
			for(Race r2 : grafo.vertexSet()) {
				if(!r1.equals(r2)) {
					double peso = this.getPesoArco(anno, r1, r2);
					if(peso != 0) {
						DefaultWeightedEdge e = grafo.addEdge(r1, r2);
						grafo.setEdgeWeight(e, peso);
					}
				}
			}
		}
		
		System.out.println("# vertici: " + grafo.vertexSet().size());
		System.out.println("# archi: " + grafo.edgeSet().size());	
	}
	
	public String calcolaBest(int anno) {
		
		double peso;
		double best = 0;
		List<Peso> ris = new ArrayList<Peso>();
		
		for(Race e1 : grafo.vertexSet()) {
			for(Race e2 : grafo.vertexSet()) {
				if(!e1.equals(e2)) {
					DefaultWeightedEdge e = grafo.getEdge(e1, e2);
					if(grafo.containsEdge(e)) {
						peso = grafo.getEdgeWeight(e);
						if(peso > best) {
							Peso better = new Peso(e1, e2, peso);
							ris.clear();
							ris.add(better);
						} else if(peso == best) {
							Peso bettertwo = new Peso(e1, e2, peso);
							ris.add(bettertwo);
						}
					}
				}
			}
		}
		
		String s = "La lista di archi di PESO MASSIMO e':";
		s = s + ris.toString();
		
		System.out.println(s);
		return s;
	}
	
	public List<Race> primoPassoSim(int anno) {
		List<Race> lista = new LinkedList<Race>();
		lista = dao.getGareAnno(anno, idMap);
		
		return lista;
	}
	
	
	
}

