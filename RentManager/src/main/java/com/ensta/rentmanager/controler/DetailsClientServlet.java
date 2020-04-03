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
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.service.ClientService;
import com.ensta.rentmanager.service.ReservationService;
import com.ensta.rentmanager.service.VehicleService;


@WebServlet("/users/details")
public class DetailsClientServlet extends HttpServlet{
	ClientService clientservice = ClientService.getInstance();
	ReservationService reservationservice = ReservationService.getInstance();
	VehicleService vehicleservice = VehicleService.getInstance();
	private static final long serialVersionUID = 1L;
	
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/details.jsp");
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println("Id"+id);
			Optional<Client> optional;
			optional = clientservice.findById(id);
			if(optional.isPresent()) {
				Client c = optional.get();
				request.setAttribute("nomUtilisateur", c.getNom());
				request.setAttribute("prenomUtilisateur", c.getPrenom());
				List<Reservation> resa;
				try {
					resa = reservationservice.findResaByClientId(id);
					request.setAttribute("nbResa", resa.size());
					request.setAttribute("rents", resa);
					if(resa.size()!=0) {
						ArrayList<Integer> carsId = new ArrayList<Integer>();
						int ind = 0;
						boolean ajout = true ;
						Reservation r = new Reservation();
						r = resa.get(0);
						carsId.add(r.getVehicle_id());
						ind += 1 ;
						if(resa.size()>1) {
							for(int i=1; i<resa.size(); i++) {
								Reservation reservation = new Reservation();
								reservation = resa.get(i);
								for(int j=0; j<carsId.size(); j++) {
									if (reservation.getVehicle_id()==carsId.get(j)) {
										ajout = false ;
									}
								}
								if(ajout) {
									carsId.add(reservation.getVehicle_id());
									ind += 1 ;
								}
								ajout = true ;
								
							}
						}
						request.setAttribute("nbCars", carsId.size());
						ArrayList<Vehicle> cars = new ArrayList<Vehicle>() ;
						for(int i=0; i<carsId.size(); i++) {
							Optional<Vehicle> optional2;
							optional2 = vehicleservice.findById(carsId.get(i));
							if(optional2.isPresent()) {
								Vehicle auto = optional2.get();
								cars.add(auto);
							}
						}
						request.setAttribute("cars", cars);
					}
					else {
						request.setAttribute("nbCars", 0);
						request.setAttribute("cars", null);
					}
				} catch (DaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			else {
				request.setAttribute("nomUtilisateur", "Une erreur est survenue");
				request.setAttribute("prenomUtilisateur", "Une erreur est survenue");
			}
			
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}

}
