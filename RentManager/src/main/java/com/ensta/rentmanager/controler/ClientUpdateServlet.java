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
import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.service.ClientService;

@WebServlet("/users/update")
public class ClientUpdateServlet extends HttpServlet{
	ClientService clientservice = ClientService.getInstance();
	private static final long serialVersionUID = 1L;
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/update.jsp");
		HttpSession session = request.getSession(true);
		session.setAttribute("errorrents", null);
		int id = Integer.parseInt(request.getParameter("id"));
			Optional<Client> optional;
			optional = clientservice.findById(id);
			if(optional.isPresent()) {
				Client c = optional.get();
				request.setAttribute("idUtilisateur", c.getId());
				request.setAttribute("nomUtilisateur", c.getNom());
				request.setAttribute("prenomUtilisateur", c.getPrenom());
				request.setAttribute("emailUtilisateur", c.getEmail());
				request.setAttribute("naissanceUtilisateur", c.getNaissance());
			}
			else {
				request.setAttribute("idUtilisateur", "Une erreur est survenue");
				request.setAttribute("nomUtilisateur", "Une erreur est survenue");
				request.setAttribute("prenomUtilisateur", "Une erreur est survenue");
				request.setAttribute("emailUtilisateur", "Une erreur est survenue");
				request.setAttribute("naissanceUtilisateur", "Une erreur est survenue");
			}
			
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(true);
		int id = Integer.parseInt(request.getParameter("id"));
		String last_name = request.getParameter("last_name");
		String first_name = request.getParameter("first_name");
		String email = request.getParameter("email");
		Date birthdate = Date.valueOf(request.getParameter("birthdate"));
		Client c = new Client();
		c.setId(id);
		c.setEmail(email);
		c.setNom(last_name);
		c.setPrenom(first_name);
		c.setNaissance(birthdate);
		RequestDispatcher dispatcher ;
	
		try {
			clientservice.update(c);
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/home.jsp");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			int idClient = Integer.parseInt(request.getParameter("id"));
			Optional<Client> optional;
			optional = clientservice.findById(idClient);
			if(optional.isPresent()) {
				Client client = optional.get();
				request.setAttribute("idUtilisateur", client.getId());
				request.setAttribute("nomUtilisateur", client.getNom());
				request.setAttribute("prenomUtilisateur", client.getPrenom());
				request.setAttribute("emailUtilisateur", client.getEmail());
				request.setAttribute("naissanceUtilisateur", client.getNaissance());
			}
			else {
				request.setAttribute("idUtilisateur", "Une erreur est survenue");
				request.setAttribute("nomUtilisateur", "Une erreur est survenue");
				request.setAttribute("prenomUtilisateur", "Une erreur est survenue");
				request.setAttribute("emailUtilisateur", "Une erreur est survenue");
				request.setAttribute("naissanceUtilisateur", "Une erreur est survenue");
			}
			session.setAttribute("errorrents", e.getMessage() );
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/update.jsp");
		}
		dispatcher.forward(request,response);
	}

}
