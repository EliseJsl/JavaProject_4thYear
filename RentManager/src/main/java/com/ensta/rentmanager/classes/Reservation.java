package com.ensta.rentmanager.classes;

import java.sql.Date;

public class Reservation {
	private int id;
	private int client_id ;
	private int vehicle_id ;
	private Date debut ;
	private Date fin ;
	
	
	public Reservation(int id, int client_id, int vehicle_id, Date debut, Date fin) {
		this.id = id ;
		this.client_id = client_id ;
		this.vehicle_id = vehicle_id;
		this.debut = debut;
		this.fin = fin ;
	}

	public Reservation() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClient_id() {
		return client_id;
	}

	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}

	public int getVehicle_id() {
		return vehicle_id;
	}

	public void setVehicle_id(int vehicle_id) {
		this.vehicle_id = vehicle_id;
	}

	public Date getDebut() {
		return debut;
	}

	public void setDebut(Date debut) {
		this.debut = debut;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", client_id=" + client_id + ", vehicle_id=" + vehicle_id + ", debut=" + debut
				+ ", fin=" + fin + "]";
	}
	
	
	
}
