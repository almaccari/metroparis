package it.corsojava.tdp.metroparis.bean;

public class Connessione {
	private int id_connessione;
	private int id_linea;
	private int id_stazP;
	private int id_stazA;
	
	public Connessione(int id_connessione, int id_linea, int id_stazP, int id_stazA) {
		super();
		this.id_connessione = id_connessione;
		this.id_linea = id_linea;
		this.id_stazP = id_stazP;
		this.id_stazA = id_stazA;
	}

	public int getId_connessione() {
		return id_connessione;
	}

	public void setId_connessione(int id_connessione) {
		this.id_connessione = id_connessione;
	}

	public int getId_linea() {
		return id_linea;
	}

	public void setId_linea(int id_linea) {
		this.id_linea = id_linea;
	}

	public int getId_stazP() {
		return id_stazP;
	}

	public void setId_stazP(int id_stazP) {
		this.id_stazP = id_stazP;
	}

	public int getId_stazA() {
		return id_stazA;
	}

	public void setId_stazA(int id_stazA) {
		this.id_stazA = id_stazA;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_connessione;
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
		Connessione other = (Connessione) obj;
		if (id_connessione != other.id_connessione)
			return false;
		return true;
	}
	
	
}
