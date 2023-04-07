package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				// System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				Corso c = new Corso( codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(c);
			}
			st.close();
			conn.close();
			rs.close();
			return corsi;
			

		} catch (SQLException e) {
			System.out.println("Error in Corso DAO");
			e.printStackTrace();
			return null;
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(String nome) {
		String sql = "SELECT codins, crediti, nome, pd "
				+ "FROM corso "
				+ "WHERE nome = ? ";
		
		Corso c = null;
		Connection conn = ConnectDB.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, nome);
			ResultSet rs = st.executeQuery();
			
			while( rs.next()) {
				c = new Corso( rs.getString("codins"), rs.getInt("crediti"), 
						rs.getString("nome"), rs.getInt("pd"));
						
			}
			rs.close();
			st.close();
			conn.close();
			return c;
		} catch (SQLException e) {
			System.err.println("Errore connessione database, corso inesistente");
			e.printStackTrace();
			return null;
		}
		
		
	}

	/*
	 * ritorna tutti i corsi a cui uno studente è iscritto, inserendo come parametro la matricola
	 */
	public List<Corso> getCorsiDiStudente(int matricola){
		
		String sql = "SELECT c.codins, c.crediti, c.nome, c.pd "
				+ "FROM  studente s, corso c,  iscrizione i "
				+ "WHERE s.matricola = i.matricola AND i.codins = c.codins AND s.matricola = ? ";
		
		List<Corso> risultato = new LinkedList<Corso>();
		
		Connection conn = ConnectDB.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				risultato.add(new Corso( rs.getString("codins"), rs.getInt("crediti"), 
						rs.getString("nome"), rs.getInt("pd")));
			}
			rs.close();
			st.close();
			conn.close();
			return risultato;
			
		} catch (SQLException e) {
			System.err.println("Errore connessione database, non esiste studente");
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	
	
	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		
		// ritorna true se l'iscrizione e' avvenuta con successo
		
		String sql = "INSERT INTO iscrizione ( matricola, codins) "
				+ "VALUES (? , ?)";
		Connection conn = ConnectDB.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());
			int rs = st.executeUpdate();
			if( rs == 1) {
				return true;
			}
		} catch (SQLException e) {
			System.err.println("Errore connessione database");
			e.printStackTrace();
			return false;
		}
		
		
		return false;
	}

}
