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

import com.ensta.rentmanager.classes.Vehicle;
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.service.VehicleService;

@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet{
	VehicleService vehicleservice = VehicleService.getInstance();
	
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp");
		HttpSession session = request.getSession(true);
		session.setAttribute("errorrents", null);
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(true);
		String manufacturer = request.getParameter("manufacturer");
		String modele = request.getParameter("modele");
		int seats = Integer.parseInt(request.getParameter("seats"));
		
		Vehicle newVehicle = new Vehicle();
		newVehicle.setConstructeur(manufacturer);
		newVehicle.setModele(modele);
		newVehicle.setNb_places(seats);
		RequestDispatcher dispatcher ;
		
		try {
			vehicleservice.create(newVehicle);
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/home.jsp");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			session.setAttribute("errorrents", e.getMessage() );
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp");
		}
		dispatcher.forward(request,response);
	}

}