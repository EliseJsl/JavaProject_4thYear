package com.ensta.rentmanager.controler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.rentmanager.classes.Client;
import com.ensta.rentmanager.classes.Reservation;
import com.ensta.rentmanager.classes.Vehicle;
import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.service.ClientService;
import com.ensta.rentmanager.service.ReservationService;
import com.ensta.rentmanager.service.VehicleService;

@WebServlet("/rents/details")
public class DetailsReservationServlet extends HttpServlet {
	ClientService clientservice = ClientService.getInstance();
	ReservationService reservationservice = ReservationService.getInstance();
	VehicleService vehicleservice = VehicleService.getInstance();
	private static final long serialVersionUID = 1L;
	
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/details.jsp");
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println("Id"+id);
			Optional<Reservation> optional;
			optional = reservationservice.findResaById(id);
			if(optional.isPresent()) {
				Reservation r = optional.get();
				int idResa = r.getId();
				int idClient = r.getClient_id();
				int idVehicle = r.getVehicle_id();
				Optional<Client> optionalclient = clientservice.findById(idClient);
				if(optionalclient.isPresent()) {
					Client c = optionalclient.get();
					request.setAttribute("idClient", c.getId());
					request.setAttribute("nomClient", c.getNom());
					request.setAttribute("prenomClient", c.getPrenom());
					request.setAttribute("emailClient", c.getEmail());
					request.setAttribute("naissanceClient", c.getNaissance());
				}
				Optional<Vehicle> optionalvehicle = vehicleservice.findById(idVehicle);
				if(optionalvehicle.isPresent()) {
					Vehicle v = optionalvehicle.get();
					request.setAttribute("idVehicle", v.getId());
					request.setAttribute("constructeurVehicle", v.getConstructeur());
					request.setAttribute("modeleVehicle", v.getModele());
					request.setAttribute("seatVehicle", v.getNb_places());
				}
				
				
				
			}
			else {
				request.setAttribute("idClient", "Une erreur est survenue");
				request.setAttribute("nomClient", "Une erreur est survenue");
				request.setAttribute("prenomClient", "Une erreur est survenue");
				request.setAttribute("emailClient", "Une erreur est survenue");
				request.setAttribute("naissanceClient", "Une erreur est survenue");
				request.setAttribute("idVehicle", "Une erreur est survenue");
				request.setAttribute("constructeurVehicle", "Une erreur est survenue");
				request.setAttribute("modeleVehicle", "Une erreur est survenue");
				request.setAttribute("seatVehicle", "Une erreur est survenue");
			}
			
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
}
