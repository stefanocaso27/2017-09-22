package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import it.polito.tdp.formulaone.model.Evento.Tipo;

public class Simulatore {
	
	private Model model;
	private PriorityQueue<Evento> queue;
	private Map<Integer, Pilota> mappa;
	private int probBox;
	private int tempoBox;
	private List<Pilota> piloti;
	private Random r = new Random();
	
	public Simulatore(int probBox, int tempoBox) {
		this.probBox = probBox;
		this.tempoBox = tempoBox;
		this.piloti = new ArrayList<Pilota>();
		
		this.mappa = new HashMap<Integer, Pilota>();
		this.model = new Model();
		this.queue = new PriorityQueue<Evento>();
	}
	
	public void init(int id) {
		model.getDao().getPilotiGara(id, mappa);
		
		this.piloti = new ArrayList<Pilota>(mappa.values());
		for(Pilota p : piloti) {
			p.setMappa(model.getDao().getTempi(id, p.getId()));
			p.setTempoGara(p.getMappa().get(1));
			Evento e = new Evento(p, p.getMappa().get(1), 1, Tipo.TRAGUARDO);
			//aggiungi giro
			queue.add(e);
		}
	}
	
	public void run() {
		while(!queue.isEmpty()) {
			Evento evento = queue.poll();
			Pilota p  = evento.getPilota();
			
			System.out.println(evento.toString() + "\n");
			
			switch(evento.getTipo()) {
				case TRAGUARDO:
					int i = r.nextInt(101);
					
					if(probBox > i) {
					//pitstop
					}
					
					for(Pilota pilota : piloti) {
						if(pilota.equals(p)) {
							
						}
					}
			}
		}
	}
	
	
}
