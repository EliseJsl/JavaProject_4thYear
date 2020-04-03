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

@WebServlet("/cars/details")
public class DetailsVehicleServlet extends HttpServlet{
	ClientService clientservice = ClientService.getInstance();
	ReservationService reservationservice = ReservationService.getInstance();
	VehicleService vehicleservice = VehicleService.getInstance();
	private static final long serialVersionUID = 1L;
	
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp");
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println("Id"+id);
			Optional<Vehicle> optional;
			optional = vehicleservice.findById(id);
			if(optional.isPresent()) {
				Vehicle v = optional.get();
				request.setAttribute("modeleVehicle", v.getModele());
				List<Reservation> resa;
				try {
					resa = reservationservice.findResaByVehicleId(id);
					request.setAttribute("nbResa", resa.size());
					request.setAttribute("rents", resa);
					if(resa.size()!=0) {
						ArrayList<Integer> clientsId = new ArrayList<Integer>();
						int ind = 0;
						boolean ajout = true ;
						Reservation r = new Reservation();
						r = resa.get(0);
						clientsId.add(r.getClient_id());
						ind += 1 ;
						if(resa.size()>1) {
							for(int i=1; i<resa.size(); i++) {
								Reservation reservation = new Reservation();
								reservation = resa.get(i);
								for(int j=0; j<clientsId.size(); j++) {
									if (reservation.getClient_id()==clientsId.get(j)) {
										ajout = false ;
									}
								}
								if(ajout) {
									clientsId.add(reservation.getClient_id());
									ind += 1 ;
								}
								ajout = true ;
								
							}
						}
						request.setAttribute("nbClient", clientsId.size());
						ArrayList<Client> clients = new ArrayList<Client>() ;
						for(int i=0; i<clientsId.size(); i++) {
							Optional<Client> optional2;
							optional2 = clientservice.findById(clientsId.get(i));
							if(optional2.isPresent()) {
								Client cli = optional2.get();
								clients.add(cli);
							}
						}
						request.setAttribute("clients", clients);
					}
					else {
						request.setAttribute("nbClient", 0);
						request.setAttribute("clients", null);
					}
				} catch (DaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			else {
				request.setAttribute("modeleVehicle", "Une erreur est survenue");
			}
			
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
}
