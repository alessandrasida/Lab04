package it.polito.tdp.lab04.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		
		List<Corso> risultato = new LinkedList<Corso>();
		risultato = model.getCorsiDiStudente(148072);
		
		for(Corso c: risultato) {
			System.out.println( c );
		}
		
		
	}

}
