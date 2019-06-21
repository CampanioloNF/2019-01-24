package it.polito.tdp.extflightdelays.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulatore {

	//grafo
	private Graph<String, DefaultWeightedEdge> grafo;
	private Model model;
	private Map<String, Integer> stateT;
	private Map<String, Integer> aggiornamenti;
	private int g;
	
	public void init(Graph<String, DefaultWeightedEdge> grafo, int g, int t, Model model, String partenza) {
		
		this.grafo = grafo;
		this.model = model;
		this.g = g;
		
		
		this.stateT = new HashMap<String, Integer>();
		
		for(String stato : grafo.vertexSet()) 
			stateT.put(stato, 0);
		
		stateT.put(partenza, t);
	}

	public void run() {
		
		//per g giorni
		for(int i=0; i<g; i++) {
			
			/*
			 * Devo avere l'accortezza di aggiornare la situzione dopo che 
			 * sia trascorso il giorno
			 */
			
			this.aggiornamenti = new HashMap<String, Integer>();
			
			//per ogni stato
			for(String stato : grafo.vertexSet()) {
			   
				List<StateAndNumber> successori = model.getSuccessori(stato);			
				//se non ci sono successori non c'è preoccupazione
				
        		if(!successori.isEmpty()) {
				//finche ci siano turisti
				if(stateT.get(stato)>0) {
				
					int turisti = stateT.get(stato);
					for(int j=0; j<turisti;j++) {
					
					//calcolo il comune divisore
            		double pesoTot = 0;
            		for(StateAndNumber sn : successori) 
            		   pesoTot+=sn.getNumV();
            		
            		//vedo dove va
            		double es = Math.random();
            		double peso = 0;
            		
            		for(StateAndNumber sn : successori) {
            		    peso+=sn.getNumV();
            		    
            		    double pro = (peso/pesoTot);
            			
            		    if(pro>es) {
            				//mando qua il turista
            		    	if(aggiornamenti.containsKey(sn.getDest())) {
        						int prec = aggiornamenti.get(sn.getDest());
        						aggiornamenti.put(sn.getDest(), prec+1);	
        					}
        					else	
        						aggiornamenti.put(sn.getDest(), 1);
            		    	
            				
            				break;
            			}
            			
            		}
            		
            		//il turista lascia lo stato per andare 
					int prima = stateT.get(stato);
            		stateT.put(stato, prima-1);
            		
				}
            
			}
				
		  }
			
	  }
			
			if(!aggiornamenti.isEmpty()) {
			for(String stato : aggiornamenti.keySet()) 
				stateT.put(stato, aggiornamenti.get(stato));
			}
		
		}
		
	}
	
	public String ris(){
		
		String ris = "Elenco degli stati e dei relativi turisti\n\n";

		List<String> stati = new LinkedList<>(stateT.keySet());
	    Collections.sort(stati);
	    
	    for(String stato : stati) 
	    	ris+=stato+"  "+stateT.get(stato)+"\n";
	    
		return ris;
	}

}
