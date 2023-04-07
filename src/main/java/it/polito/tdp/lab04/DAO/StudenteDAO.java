package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	public Map<Integer, Studente> getIscrittiCorso(){
		String sql = "SELECT matricola, cognome, nome, CDS "
				+ "FROM studente ";
		
		Map<Integer, Studente> risultato = new HashMap<Integer, Studente>();
		
		Connection conn = ConnectDB.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				risultato.put(rs.getInt("matricola"), new Studente(rs.getInt("matricola"), rs.getString("cognome"),
						rs.getString("nome"), rs.getString("CDS")));
				
			}
			rs.close();
			st.close();
			conn.close();
			return risultato;
			
		} catch (SQLException e) {
			System.err.println("Errore connessione database");
			e.printStackTrace();
			return null;
		}
		
				
	}
	
	public List<Studente> getIscrittiCorsoSpecifico( String codins){
		
		String sql = "SELECT s.matricola, s.cognome, s.nome, s.CDS "
			 	+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola = i.matricola AND i.codins = ?";
		
		List<Studente> risultato = new ArrayList<Studente>();
		
		Connection conn = ConnectDB.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				risultato.add(new Studente( rs.getInt("matricola"), rs.getString("cognome"),
						rs.getString("nome"), rs.getString("CDS")));
				
			}
			rs.close();
			st.close();
			conn.close();
			return risultato;
			
		} catch (SQLException e) {
			System.err.println("Errore connessione database");
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
}
