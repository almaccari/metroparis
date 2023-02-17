package it.corsojava.tdp.metroparis.bean;

import java.util.ArrayList;
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
import it.corsojava.tdp.metroparis.frontend.RegistraAlberoDiVisita;

public class Model {

//	Definisco delle variabili globali alla classe
//	Lista di tutte le fermate
	private List<Fermata> fermate;
//	Creo una mappa che mi tiene l'associazione idFermata con il relativo oggetto Fermata
	private Map<Integer, Fermata> fermateIdMap;
//	Grafo comprensivo di tutti i nodi e archi
	private Graph<Fermata, DefaultEdge> grafo;
	
	public List<Fermata> getFermate() {
//		Il blocco if ci permette di evitare che, ad ogni chiamata del metodo, vengano ricaricate fermate e la Mappa
		if(this.fermate==null) {
			MetroDAO dao = new MetroDAO();
			this.fermate = dao.getAllFermate();

			this.fermateIdMap = new HashMap<Integer, Fermata>();
			for(Fermata f: this.fermate) {
				this.fermateIdMap.put(f.getId_fermata(), f);
			}
		}
		return this.fermate;
	}
	
	public List<Fermata> calcolaPercorso(Fermata partenza, Fermata arrivo) {
//		Anzitutto creo il grafo comprensivo di tutti i vertici e archi
		creaGrafo();
//		Il metodo visitaGrafo effettuerà un giro del grafo in ampiezza e raccoglierà info -- SONO IN ATTESA 1
		Map<Fermata, Fermata> alberoInverso = visitaGrafo(partenza);
//		ADESSO DOVREI TORNARE AL CONTROLLER LA LISTA DEI NODI CHE NECESSITA VISITARE PER ANDARE DA A a B
		Fermata corrente = arrivo;
		List<Fermata> percorso = new ArrayList<Fermata>();
		while(corrente != null) {
			percorso.add(0, corrente);
			corrente = alberoInverso.get(corrente);
		}
		return percorso;
	}
	
	
	public void creaGrafo() {
//		Utilizzo una struttura a grafo semplice orientato, con archi tipo Default
		this.grafo = new SimpleDirectedGraph<Fermata, DefaultEdge>(DefaultEdge.class);
//		Utilizzo l'utility di Graphs per caricare in un comando solo tutte le fermate
		Graphs.addAllVertices(this.grafo, getFermate());
//		Istanzio un oggetto DAO per prepararmi ad interrogare il DB
		MetroDAO dao = new MetroDAO();
//		Ho creato un oggetto CoppiaId che uso per contenere i numeri di linea delle fermate da collegare
//		La lista contiene tutti i collegamenti necessari per collegare la partenza con l'arrivo
		List<CoppiaID> fermateDaCollegare = dao.getAllFermateConnesse();
		for(CoppiaID coppia: fermateDaCollegare) {
//			Carico nel grafo tutti gli archi individuati dalla coppia idPartenza e idArrivo
			this.grafo.addEdge(fermateIdMap.get(coppia.getIdPartenza()), fermateIdMap.get(coppia.getIdArrivo()));
		}
/*		System.out.println("Elenco vertici:"+this.grafo);
		System.out.println(fermateIdMap);
		System.out.println("Numero di vertici nel grafo: "+ this.grafo.vertexSet().size());
		System.out.println("Numero di archi nel grafo: "+ this.grafo.edgeSet().size());
		visitaGrafo(fermate.get(100));*/
	}

	public Map<Fermata, Fermata> visitaGrafo(Fermata partenza) {
//		Creo un oggetto iteratore, in ampiezza, di grafo e gli passo la partenza
//		alla fine visita conterrà l'albero di visita del grafo
		GraphIterator<Fermata, DefaultEdge> visita = new BreadthFirstIterator<>(this.grafo, partenza);
//		Creo una Mappa che mi raccoglierà la visita inversa dell'albero e verrà popolata da RegistraAlberoDiVisita
		Map<Fermata, Fermata> alberoInverso = new HashMap();
		alberoInverso.put(partenza,  null);
//		Applico all'Iteratore un ascoltatore che innesca degli eventi quando un certo nodo o arco è stato visitato
//		RegistraAlberoDiVisita è una classe che viene auto creata e che raccoglie gli eventi gestiti dall'ascoltatore
//		vado a popolare con il codice soltanto gli eventi che mi interessano 
		visita.addTraversalListener(new RegistraAlberoDiVisita(alberoInverso, this.grafo));
//		Quando percorro i vari vertici dell'albero di visita si innesca il listner che registra gli archi inversi
		while(visita.hasNext()) {
			Fermata f = visita.next();
//			System.out.println(f);
		}
		return alberoInverso;
	}
}
