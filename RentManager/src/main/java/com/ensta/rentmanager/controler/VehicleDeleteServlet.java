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

import com.ensta.rentmanager.classes.Reservation;
import com.ensta.rentmanager.classes.Vehicle;
import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.service.ReservationService;
import com.ensta.rentmanager.service.VehicleService;

@WebServlet("/cars/delete")
public class VehicleDeleteServlet extends HttpServlet {
	VehicleService vehicleservice = VehicleService.getInstance();
	ReservationService reservationservice = ReservationService.getInstance();
	private static final long serialVersionUID = 1L;
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/delete.jsp");
		int id_vehicle = Integer.parseInt(request.getParameter("id"));
		Optional<Vehicle> optional;
		optional = vehicleservice.findById(id_vehicle);
		if(optional.isPresent()) {
			Vehicle v = optional.get();
			try {
				List<Reservation> resa = (ArrayList<Reservation>) reservationservice.findResaByVehicleId(id_vehicle);
				for(int i = 0;i<resa.size();i++) {
					reservationservice.delete(resa.get(i));
				}
			} catch (DaoException | ServiceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				vehicleservice.delete(v);
			
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println("ERREUR");
		}
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
}
