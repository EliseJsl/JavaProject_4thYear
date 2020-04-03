package com.ensta.rentmanager.controler;

import java.io.IOException;
import java.sql.Date;

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
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.service.ClientService;
import com.ensta.rentmanager.service.ReservationService;
import com.ensta.rentmanager.service.VehicleService;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet{
	ReservationService reservationservice = ReservationService.getInstance();
	VehicleService vehiculeservice = VehicleService.getInstance();
	ClientService clientservice = ClientService.getInstance();
	
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp");
		HttpSession session = request.getSession(true);
		session.setAttribute("errorrents", null);
		try {
			request.setAttribute("vehicles", vehiculeservice.findAll());
		} catch (ServiceException e) {
			request.setAttribute("nbVehicles", "Une erreur est survenue");
		}
		try {
			request.setAttribute("users", clientservice.findAll());
		} catch (ServiceException e) {
			request.setAttribute("nbUtilisateurs", "Une erreur est survenue");
		}
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(true);
		Date debut = Date.valueOf(request.getParameter("begin"));
		Date fin = Date.valueOf(request.getParameter("end"));
		int car = Integer.parseInt(request.getParameter("car"));
		int client = Integer.parseInt(request.getParameter("client"));
		
		Reservation newreservation = new Reservation();
		newreservation.setClient_id(client);
		newreservation.setVehicle_id(car);
		newreservation.setDebut(debut);
		newreservation.setFin(fin);
		
		RequestDispatcher dispatcher ;
		
		try {
			reservationservice.create(newreservation);
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/home.jsp");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			try {
				request.setAttribute("vehicles", vehiculeservice.findAll());
				request.setAttribute("users", clientservice.findAll());
			} catch (ServiceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			session.setAttribute("errorrents", e.getMessage() );
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp");
		}
		dispatcher.forward(request,response);
		
	}
	
	

}
