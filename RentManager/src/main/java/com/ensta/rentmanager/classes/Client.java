package com.ensta.rentmanager.classes;
import java.sql.Date ;

public class Client { 
	private int id;
	private String nom ;
	private String prenom ;
	private String email ;
	private Date naissance ;
	
public Client (int id, String nom, String prenom, String email, Date naissance) {
	this.nom=nom ;
	this.prenom = prenom ;
	this.email = email ;
	this.naissance = naissance ;
	this.id = id ;
}

public Client() {
	
}


public int getId() {
	return this.id ;
}

public void setId(int id) {
	this.id = id ;
}

public String getNom() {
	return nom;
}

public void setNom(String nom) {
	this.nom = nom;
}

public String getPrenom() {
	return prenom;
}

public void setPrenom(String prenom) {
	this.prenom = prenom;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public Date getNaissance() {
	return naissance;
}

public void setNaissance(Date naissance) {
	this.naissance = naissance;
}

@Override
public String toString() {
	return "Client [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", naissance=" + naissance
			+ "]";
}


}
