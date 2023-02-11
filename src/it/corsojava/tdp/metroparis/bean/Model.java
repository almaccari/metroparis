package it.corsojava.tdp.metroparis.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.corsojava.tdp.metroparis.db.MetroDAO;

public class Model {

	private Graph<Fermata, DefaultEdge> grafo;
	
	public void creaGrafo() {
		this.grafo = new SimpleDirectedGraph<Fermata, DefaultEdge>(DefaultEdge.class);
		MetroDAO dao = new MetroDAO();
		List<Fermata> fermate = dao.getAllFermate();
		Graphs.addAllVertices(this.grafo, fermate);
		Map<Integer, Fermata> fermateIdMap = new HashMap<Integer, Fermata>();
		for(Fermata f:fermate) {
			fermateIdMap.put(f.getId_fermata(), f);
		}

/*	METODO 1: Itero su ogni coppia di vertici	

  		for(Fermata partenza: fermate) {
			for(Fermata arrivo: fermate) {
				if(dao.isFermateConnesse(partenza, arrivo)) {
					this.grafo.addEdge(partenza, arrivo);
				}
			}
		}
*/
		
//		METODO 2: Dato ciascun vertice, valuto i nodi adiacenti
/*		for(Fermata partenza: fermate) {
			List<Integer> idConnesse = dao.getIdFermateConnesse(partenza);
			for(Integer id: idConnesse) {
				Fermata arrivo = null;
				for(Fermata f: fermate) {
					if(f.getId_fermata() == id) {
						arrivo = f;
						break;
					}
				}
				this.grafo.addEdge(partenza, arrivo);
			}
		}*/
		
//		METODO 2b: FAccio fare alla query l'estrazione degli arrivi
/*		for(Fermata partenza: fermate) {
			List<Fermata> arrivi = dao.getFermateConnesse(partenza);
			for(Fermata arrivo: arrivi) {
				this.grafo.addEdge(partenza, arrivo);
			}
		}*/
//		METODO 2c: Aggiungo una mappa che mi serve per trovare rapidamente l'oggetto nodo di destinazione
/*		for(Fermata partenza: fermate) {
			List<Integer> idConnesse = dao.getIdFermateConnesse(partenza);
			for(Integer id: idConnesse) {
				Fermata arrivo = fermateIdMap.get(id);
				this.grafo.addEdge(partenza, arrivo);
			}
		}*/
//		METODO 3: Lascia fare tutto alla query sql che mi ritorna una lista di oggetti coppia fermate
		List<CoppiaID> fermateDaCollegare = dao.getAllFermateConnesse();
		for(CoppiaID coppia: fermateDaCollegare) {
			this.grafo.addEdge(fermateIdMap.get(coppia.getIdPartenza()), fermateIdMap.get(coppia.getIdArrivo()));
		}
		System.out.println("Elenco vertici:"+this.grafo);
		System.out.println(fermateIdMap);
		System.out.println("Numero di vertici nel grafo: "+ this.grafo.vertexSet().size());
		System.out.println("Numero di archi nel grafo: "+ this.grafo.edgeSet().size());
		visitaGrafo(fermate.get(100));
	}

	public void visitaGrafo(Fermata partenza) {
		GraphIterator<Fermata, DefaultEdge> visita = new DepthFirstIterator<>(this.grafo, partenza);
		System.out.println("Stazione di partenza: "+ partenza.getNome());
		while(visita.hasNext()) {
			Fermata f = visita.next();
			System.out.println(f);
		}
	}
}
