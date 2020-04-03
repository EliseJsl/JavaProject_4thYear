package com.ensta.rentmanager.controler;

import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ensta.rentmanager.classes.Client;
import com.ensta.rentmanager.classes.Reservation;
import com.ensta.rentmanager.classes.Vehicle;
import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.service.ClientService;
import com.ensta.rentmanager.service.ReservationService;
import com.ensta.rentmanager.service.VehicleService;

@WebServlet("/rents/update")
public class ReservationUpdateServlet extends HttpServlet {
	ReservationService reservationservice = ReservationService.getInstance();
	VehicleService vehicleservice = VehicleService.getInstance();
	ClientService clientservice = ClientService.getInstance();
	private static final long serialVersionUID = 1L;
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/update.jsp");
		HttpSession session = request.getSession(true);
		session.setAttribute("errorrents", null);
		try {
			request.setAttribute("vehicles", vehicleservice.findAll());
			request.setAttribute("users", clientservice.findAll());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int id = Integer.parseInt(request.getParameter("id"));
			Optional<Reservation> optional;
			optional = reservationservice.findResaById(id);
			if(optional.isPresent()) {
				Reservation r = optional.get();
				request.setAttribute("idReservation", r.getId());
				request.setAttribute("idClientReservation", r.getClient_id());
				request.setAttribute("idVehicleReservation", r.getVehicle_id());
				request.setAttribute("debutReservation", r.getDebut());
				request.setAttribute("finReservation", r.getFin());
				
				Optional<Vehicle> optionalvehicle = vehicleservice.findById(r.getVehicle_id());
				if(optionalvehicle.isPresent()) {
					Vehicle v = optionalvehicle.get();
					request.setAttribute("idVehicleReservation", v.getId());
					request.setAttribute("modeleVehicleReservation", v.getModele());
				}
				
				Optional<Client> optionalclient = clientservice.findById(r.getClient_id());
				if(optionalclient.isPresent()) {
					Client c = optionalclient.get();
					request.setAttribute("idClientReservation", c.getId());
					request.setAttribute("nomClientReservation", c.getNom());
					request.setAttribute("prenomClientReservation", c.getPrenom());
				}
				
			}
			else {
				request.setAttribute("idReservation", "Une erreur est survenue");
				request.setAttribute("idClientReservation", "Une erreur est survenue");
				request.setAttribute("idVehicleReservation", "Une erreur est survenue");
				request.setAttribute("debutReservation", "Une erreur est survenue");
				request.setAttribute("finReservation", "Une erreur est survenue");
				request.setAttribute("idVehicleReservation", "Une erreur est survenue");
				request.setAttribute("modeleVehicleReservation", "Une erreur est survenue");
				request.setAttribute("idClientReservation", "Une erreur est survenue");
				request.setAttribute("nomClientReservation", "Une erreur est survenue");
				request.setAttribute("prenomClientReservation", "Une erreur est survenue");
			}
			
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(true);
		int id = Integer.parseInt(request.getParameter("id"));
		Date debut = Date.valueOf(request.getParameter("begin"));
		Date fin = Date.valueOf(request.getParameter("end"));
		int car = Integer.parseInt(request.getParameter("car"));
		int client = Integer.parseInt(request.getParameter("client"));
		Reservation r = new Reservation();
		r.setId(id);
		r.setClient_id(client);
		r.setVehicle_id(car);
		r.setDebut(debut);
		r.setFin(fin);
		RequestDispatcher dispatcher ;
	
		try {
			reservationservice.update(r);
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/home.jsp");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			try {
				request.setAttribute("vehicles", vehicleservice.findAll());
				request.setAttribute("users", clientservice.findAll());
			} catch (ServiceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int idResa = Integer.parseInt(request.getParameter("id"));
			Optional<Reservation> optional;
			optional = reservationservice.findResaById(idResa);
			if(optional.isPresent()) {
				Reservation resa = optional.get();
				request.setAttribute("idReservation", resa.getId());
				request.setAttribute("idClientReservation", resa.getClient_id());
				request.setAttribute("idVehicleReservation", resa.getVehicle_id());
				request.setAttribute("debutReservation", resa.getDebut());
				request.setAttribute("finReservation", resa.getFin());
				
				Optional<Vehicle> optionalvehicle = vehicleservice.findById(r.getVehicle_id());
				if(optionalvehicle.isPresent()) {
					Vehicle v = optionalvehicle.get();
					request.setAttribute("idVehicleReservation", v.getId());
					request.setAttribute("modeleVehicleReservation", v.getModele());
				}
				
				Optional<Client> optionalclient = clientservice.findById(r.getClient_id());
				if(optionalclient.isPresent()) {
					Client c = optionalclient.get();
					request.setAttribute("idClientReservation", c.getId());
					request.setAttribute("nomClientReservation", c.getNom());
					request.setAttribute("prenomClientReservation", c.getPrenom());
				}
				
			}
			else {
				request.setAttribute("idReservation", "Une erreur est survenue");
				request.setAttribute("idClientReservation", "Une erreur est survenue");
				request.setAttribute("idVehicleReservation", "Une erreur est survenue");
				request.setAttribute("debutReservation", "Une erreur est survenue");
				request.setAttribute("finReservation", "Une erreur est survenue");
				request.setAttribute("idVehicleReservation", "Une erreur est survenue");
				request.setAttribute("modeleVehicleReservation", "Une erreur est survenue");
				request.setAttribute("idClientReservation", "Une erreur est survenue");
				request.setAttribute("nomClientReservation", "Une erreur est survenue");
				request.setAttribute("prenomClientReservation", "Une erreur est survenue");
			}
			session.setAttribute("errorrents", e.getMessage() );
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/update.jsp");
		}
		dispatcher.forward(request,response);
	}
}
