package it.corsojava.tdp.metroparis.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.corsojava.tdp.metroparis.bean.CoppiaID;
import it.corsojava.tdp.metroparis.bean.Fermata;

public class MetroDAO {

	public List<Fermata> getAllFermate() {
		final String sql = "SELECT id_fermata, nome, coordX, coordY FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_fermata"), rs.getString("nome"), rs.getDouble("coordX"), rs.getDouble("coordY"));
				fermate.add(f);
			}
			st.close();
			conn.close();
			return fermate;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Errore di accesso ala DB", e);
		}


	}
	
	public boolean isFermateConnesse(Fermata partenza, Fermata arrivo) {
		final String sql = "SELECT COUNT(*) AS cnt FROM connessione WHERE id_stazP = ? AND id_stazA = ?";
		Connection conn = DBConnect.getConnection();
		PreparedStatement st;
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, partenza.getId_fermata());
			st.setInt(2, arrivo.getId_fermata());
			ResultSet res = st.executeQuery();
			res.first();
			int count = res.getInt("cnt");
			st.close();
			conn.close();
			return count>0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al DB", e);
		}
	}

	public List<Integer> getIdFermateConnesse(Fermata partenza) {
		String sql = "SELECT id_stazA FROM connessione WHERE id_stazP = ? GROUP BY id_stazA";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, partenza.getId_fermata());
			ResultSet rs = st.executeQuery();
			List<Integer> result = new ArrayList<>();
			while(rs.next()) {
				result.add(rs.getInt("id_stazA"));
			}
			rs.close();
			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Fermata> getFermateConnesse(Fermata partenza) {
		final String sql = "SELECT * FROM fermata WHERE id_fermata IN "
				+ "(SELECT id_stazA FROM connessione WHERE id_stazP = ? GROUP BY id_stazA) ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, partenza.getId_fermata());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_fermata"), rs.getString("nome"), rs.getDouble("coordX"), rs.getDouble("coordY"));
				fermate.add(f);
			}
			st.close();
			conn.close();
			return fermate;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Errore di accesso ala DB", e);
		}
	}
	
	public List<CoppiaID> getAllFermateConnesse() {
//		Questa query ci assicura di estrarre tutti gli archi senza duplicati
		String sql = "SELECT DISTINCT id_stazP, id_stazA " + 
				"FROM connessione";
		Connection conn = DBConnect.getConnection();
		PreparedStatement st;
		try {
			st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<CoppiaID> archi = new ArrayList<CoppiaID>();
			while(rs.next()) {
				CoppiaID cId = new CoppiaID(rs.getInt("id_stazP"), rs.getInt("id_stazA"));
				archi.add(cId);
			}
			st.close();
			conn.close();
//			Ci ritorna tutti gli archi del del grafo (cioè tutte le connessioni tra le varie fermate)
			return archi;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	
	
	
	
	
	
}
