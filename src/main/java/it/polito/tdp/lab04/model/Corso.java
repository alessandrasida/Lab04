package it.polito.tdp.lab04.model;

import java.util.Objects;

public class Corso {
	private String codins;
	private int crediti;
	private String nome;
	private int pd;
	public Corso(String codins, int crediti, String nome, int pd) {
		this.codins = codins;
		this.crediti = crediti;
		this.nome = nome;
		this.pd = pd;
	}
	public String getCodins() {
		return codins;
	}
	public int getCrediti() {
		return crediti;
	}
	public String getNome() {
		return nome;
	}
	public int getPd() {
		return pd;
	}
	public void setCodins(String codins) {
		this.codins = codins;
	}
	public void setCrediti(int crediti) {
		this.crediti = crediti;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setPd(int pd) {
		this.pd = pd;
	}
	@Override
	public int hashCode() {
		return Objects.hash(codins);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Corso other = (Corso) obj;
		return Objects.equals(codins, other.codins);
	}
	@Override
	public String toString() {
		return   codins + " " + crediti + " " + nome + " " + pd ;
	}

	
	
}
