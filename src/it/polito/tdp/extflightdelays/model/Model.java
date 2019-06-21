package it.polito.tdp.extflightdelays.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	private Graph<String, DefaultWeightedEdge> grafo;
	private Set<String> stati;
	private ExtFlightDelaysDAO dao;
	private Simulatore sim;
	
	public Model() {
		this.dao = new ExtFlightDelaysDAO();
		this.sim = new Simulatore();
	}
	
	public void creaGrafo() {
		
		//creo il grafo
       this.grafo = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
	   this.stati = new HashSet<String>();
	   
	  //carico tutto
	   dao.loadGraph(stati, grafo);
	   System.out.println("Grafo creato "+grafo.vertexSet().size()+"  "+grafo.edgeSet().size());
	   
	}

	public List<String> getVertici() {
		
		List<String> ris = new LinkedList<>(grafo.vertexSet());
		Collections.sort(ris);
		
		return ris;
	}

	public List<StateAndNumber> getSuccessori(String state) {
	
		if(grafo!=null) {
		
			
		
		List<StateAndNumber> list = new LinkedList<>();
		
		if(Graphs.successorListOf(grafo, state).size()>0) {
		
		for(String stato : Graphs.successorListOf(grafo, state)) 
		    list.add(new StateAndNumber(state, stato, (int) grafo.getEdgeWeight(grafo.getEdge(state, stato))));
		
		Collections.sort(list);
		
		}
		
		return list;
	    	
		}
		return null;
	}

	public String simulate(int g, int t, String partenza) {
		
		if(grafo!=null) {
			
			sim.init(grafo, g, t, this, partenza);
			sim.run();
			return sim.ris();
		}
		return "";
	}
}
