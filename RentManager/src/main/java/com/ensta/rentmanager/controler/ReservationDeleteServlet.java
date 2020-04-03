package com.ensta.rentmanager.controler;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.rentmanager.classes.Reservation;
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.service.ReservationService;

@WebServlet("/rents/delete")
public class ReservationDeleteServlet extends HttpServlet{
	ReservationService reservationservice = ReservationService.getInstance();
	private static final long serialVersionUID = 1L;
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/delete.jsp");
		int id_resa = Integer.parseInt(request.getParameter("id"));
		Optional<Reservation> optional;
		optional = reservationservice.findResaById(id_resa);
		if(optional.isPresent()) {
			Reservation r = optional.get();
			try {
				reservationservice.delete(r);
				
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
