package it.polito.tdp.lab04.model;

import java.util.List;
import java.util.Map;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	private CorsoDAO corsoDAO;
	private StudenteDAO studenteDAO;
	
	public Model() {
		this.corsoDAO = new CorsoDAO();
		this.studenteDAO = new StudenteDAO();
	}
	
	//riturna una lista di corsi presenti del db
	public List<Corso> getTuttiICorsi(){
		return this.corsoDAO.getTuttiICorsi();
	}
	
	public Map<Integer, Studente> getTuttiStudenti(){
		return this.studenteDAO.getIscrittiCorso();
	}
	
	public List<Studente> getIscrittiCorso( String codins){
		return this.studenteDAO.getIscrittiCorsoSpecifico(codins);
	}
	
	public List<Corso> getCorsiDiStudente(int matricola){
		return this.corsoDAO.getCorsiDiStudente(matricola);
	}
	
	public Corso getCorso( String nome) {
		return this.corsoDAO.getCorso(nome);
	}
	
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		return this.corsoDAO.inscriviStudenteACorso(studente, corso);
	}
}
