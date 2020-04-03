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

import com.ensta.rentmanager.classes.Vehicle;
import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.service.VehicleService;

@WebServlet("/cars/update")
public class VehicleUpdateServlet extends HttpServlet {
	VehicleService vehicleservice = VehicleService.getInstance();
	private static final long serialVersionUID = 1L;
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/update.jsp");
		HttpSession session = request.getSession(true);
		session.setAttribute("errorrents", null);
		int id = Integer.parseInt(request.getParameter("id"));
			Optional<Vehicle> optional;
			optional = vehicleservice.findById(id);
			if(optional.isPresent()) {
				Vehicle v = optional.get();
				request.setAttribute("idVehicle", v.getId());
				request.setAttribute("constructeurVehicle", v.getConstructeur());
				request.setAttribute("modeleVehicle", v.getModele());
				request.setAttribute("seatVehicle", v.getNb_places());
			}
			else {
				request.setAttribute("idVehicle", "Une erreur est survenue");
				request.setAttribute("constructeurVehicle", "Une erreur est survenue");
				request.setAttribute("modeleVehicle", "Une erreur est survenue");
				request.setAttribute("seatVehicle", "Une erreur est survenue");
			}
			
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(true);
		int id = Integer.parseInt(request.getParameter("id"));
		String constructeur = request.getParameter("manufacturer");
		String modele = request.getParameter("modele");
		int seat = Integer.parseInt(request.getParameter("seats"));
		Vehicle v = new Vehicle();
		v.setId(id);
		v.setConstructeur(constructeur);
		v.setModele(modele);
		v.setNb_places(seat);
		RequestDispatcher dispatcher ;
	
		try {
			vehicleservice.update(v);
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/home.jsp");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			int idVehicle = Integer.parseInt(request.getParameter("id"));
			Optional<Vehicle> optional;
			optional = vehicleservice.findById(idVehicle);
			if(optional.isPresent()) {
				Vehicle vehicle = optional.get();
				request.setAttribute("idVehicle", vehicle.getId());
				request.setAttribute("constructeurVehicle", vehicle.getConstructeur());
				request.setAttribute("modeleVehicle", vehicle.getModele());
				request.setAttribute("seatVehicle", vehicle.getNb_places());
			}
			else {
				request.setAttribute("idVehicle", "Une erreur est survenue");
				request.setAttribute("constructeurVehicle", "Une erreur est survenue");
				request.setAttribute("modeleVehicle", "Une erreur est survenue");
				request.setAttribute("seatVehicle", "Une erreur est survenue");
			}
			session.setAttribute("errorrents", e.getMessage() );
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/update.jsp");
		}
		dispatcher.forward(request,response);
	}
}
