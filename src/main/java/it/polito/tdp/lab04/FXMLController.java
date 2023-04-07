package it.polito.tdp.lab04;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	@FXML
	private CheckBox bottoneCheck;
	  
	private Model model;
    @FXML
    
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cmbCorsi;

    
    @FXML
    private TextField txtCognome;

    @FXML
    private TextField txtMatricola;

    @FXML
    private TextField txtNome;

    public void setModel(Model model) {
		this.model = model;
		List<Corso> corsi = this.model.getTuttiICorsi();
		this.cmbCorsi.getItems().add(null);
        for(Corso c: corsi) {
        	this.cmbCorsi.getItems().add(c.getNome());
        }
        
	}
    
    @FXML
    private TextArea txtRisultato;
    
    
    @FXML
    void doCercaCorsi(ActionEvent event) {
    	this.txtRisultato.clear();
    	try {
    		List<Corso> risultato = this.model.getCorsiDiStudente(Integer.parseInt(this.txtMatricola.getText()));
	    		if(risultato.size() != 0) {
	    		for(Corso c: risultato) {
	    			this.txtRisultato.appendText(c+ "\n");
	    			}
	    		}
	    		else {
	    			this.txtRisultato.setText("Studente non iscritto ad alcun corso.");
	    		}
    	}catch( NumberFormatException e) {
    		this.txtRisultato.setText("Inserire un formato corretto o una matricola valida.");
			return;
    	}
    	
    	
    	
    	
    }

    @FXML
    void doCercaIscrittiCorso(ActionEvent event) {
    	this.txtRisultato.clear();
    	List<Corso> corsi = this.model.getTuttiICorsi();
    	String nomeC = this.cmbCorsi.getValue();
    	String codice = "";
    	if( nomeC != null) {
    		Corso c = this.model.getCorso(nomeC);
    		codice = c.getCodins();
    		}
    	// caso in cui viene inserito il corso ma non la matricola> ritorna tutti gli studenti iscritti a quel corso
    	if( codice != null && this.txtMatricola.getText().isEmpty() == true) {
    		if(this.model.getIscrittiCorso(codice).size() != 0 ) {
	    		for( Studente s: this.model.getIscrittiCorso(codice)) {
	    			this.txtRisultato.appendText(s + "\n");	
	    		}
    		}
    		else
    			this.txtRisultato.setText("Corso senza iscritti.");
    		return;
    		}
    	// caso in cui viene inserito sia il corso che la matricola> torna se è iscritto o no lo studente al corso scelto
		if( codice != null && this.txtMatricola.getText().isEmpty() == false) {
			try {
				int matricola = Integer.parseInt(this.txtMatricola.getText());
				for( Studente st : this.model.getIscrittiCorso(codice)) {
				if(matricola == st.getMatricola()) {
					this.txtRisultato.setText("Studente già iscritto a questo corso.");
					return;
					}
				}
			}catch(  NumberFormatException e) {
				this.txtRisultato.setText("Inserire un formato corretto o una matricola valida.");
				return;
			}
			this.txtRisultato.setText("Studente non iscritto a questo corso.");
		}
    	else {
    		this.txtRisultato.setText("Non è stato selezionato alcun corso.");
    	}
    	
    	
    }

    @FXML
    void doIscriviti(ActionEvent event) {
    	this.txtRisultato.clear();
    	String nomeC = this.cmbCorsi.getValue();
    	if( nomeC != null) {
    		Corso c = this.model.getCorso(nomeC);
    		List<Corso> corsi = new LinkedList();
    		try {
    		 corsi = this.model.getCorsiDiStudente(Integer.parseInt(this.txtMatricola.getText()));
    		}catch( NumberFormatException e) {
    			this.txtRisultato.setText("Inserire un formato corretto o una matricola valida.");
    			return;
    		}
    		boolean presente = false;
    		for( Corso cc : corsi) {
    			if( cc.getCodins().compareTo(c.getCodins())==0)
    				presente = true;
    		}
    		//se non risulta iscritto al corso, viene iscritto
    		if (presente == false) {
        		this.txtRisultato.appendText( c.getNome() + "entra");
    			Map<Integer, Studente> studenti = this.model.getTuttiStudenti();
    			Studente s = studenti.get(Integer.parseInt(this.txtMatricola.getText()));
    			boolean fatto = this.model.inscriviStudenteACorso(s, c);
    			if( fatto == true)
    				this.txtRisultato.setText("Iscrizione avvenuta con successo.");
    			else
    				this.txtRisultato.setText("Iscrizione fallita.");
    			return;
    		}
    		if( presente == true) {
    			this.txtRisultato.setText("Studente già iscritto a questo corso.");
    			return;
    		}
    	}
    	else {
    		this.txtRisultato.setText("Selezionare il corso a cui iscriversi.");
    		return;
    	}
    	
    	
    	
    }

    @FXML
    void doReset(ActionEvent event) {
    	this.txtCognome.clear();
    	this.txtMatricola.clear();
    	this.txtNome.clear();
    	this.txtRisultato.clear();
    	this.bottoneCheck.setSelected(false);
    	
    	
    }
    
    @FXML
    void doCheck(ActionEvent event) {
    	/*
    	 * Mappa che ha come chiave la matricola e valore uno studente> cerco lo studente, se non presente ritorno testo
    	 * il try serve per vedere se il formato è corretto
    	 */
    	try {
	    	Studente input = this.model.getTuttiStudenti().get(Integer.parseInt(txtMatricola.getText()));
	    	if( input != null) {
		    	this.txtNome.setText(input.getNome());
		    	this.txtCognome.setText(input.getCognome());
	    	}
	    	else {
	    		this.txtRisultato.setText("Matricola non presente nel sistema.");
	    	}
    	}catch( NumberFormatException e) {
    		this.txtRisultato.setText("Inserire un formato valido.");
    	}
    	
    }
    
    @FXML
    void initialize() {
        assert bottoneCheck != null : "fx:id=\"bottoneCheck\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCorsi != null : "fx:id=\"cmbCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
		
        
    }
    
    

}
