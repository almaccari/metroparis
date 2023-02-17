package it.corsojava.tdp.metroparis.bean;

public class CoppiaID {
//	Questo oggetto contiene la coppia numero fermata di partenza e numero fermata di arrivo di un certo arco
	int idPartenza;
	int idArrivo;
	
	public CoppiaID(int idPartenza, int idArrivo) {
		super();
		this.idPartenza = idPartenza;
		this.idArrivo = idArrivo;
	}

	public int getIdPartenza() {
		return idPartenza;
	}

	public void setIdPartenza(int idPartenza) {
		this.idPartenza = idPartenza;
	}

	public int getIdArrivo() {
		return idArrivo;
	}

	public void setIdArrivo(int idArrivo) {
		this.idArrivo = idArrivo;
	}
	
	
}
