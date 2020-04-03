package com.ensta.rentmanager.controler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.service.ClientService;
import com.ensta.rentmanager.service.ReservationService;
import com.ensta.rentmanager.service.VehicleService;



@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	
	ClientService clientservice = ClientService.getInstance();
	VehicleService vehicleservice = VehicleService.getInstance();
	ReservationService reservationservice = ReservationService.getInstance();
	
	private static final long serialVersionUID = 1L;
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/home.jsp");
		try {
			request.setAttribute("nbUtilisateurs", clientservice.findAll().size());
			request.setAttribute("nbVoitures", vehicleservice.findAll().size());
			request.setAttribute("nbReservations", reservationservice.findAll().size());
		} catch (ServiceException e) {
			request.setAttribute("nbUtilisateurs", "Une erreur est survenue");
			request.setAttribute("nbVoitures", "Une erreur est survenue");
			request.setAttribute("nbReservations", "Une erreur est survenue");
		}
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
}
