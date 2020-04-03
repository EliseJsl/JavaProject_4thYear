package com.ensta.rentmanager.validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.ensta.rentmanager.classes.Client;
import com.ensta.rentmanager.dao.ClientDao;
import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.exception.ServiceException;

public class ClientValidator {
	private boolean test;
	private static ClientValidator instance = null;
	private static ClientValidator instanceTest = null;
	private ClientValidator() {}
	private ClientValidator(boolean test) {
		this.test = test;
	}
	public static ClientValidator getInstance(boolean test) {
		if(test) {
		if(instance == null) {
			instance = new ClientValidator(true);
		}
		return instanceTest;
		} else {
			if(instance == null) {
				instance = new ClientValidator(false);
			}
		}
		return instance;
	}
	public static ClientValidator getInstance() {
		return getInstance(false);
	}

	ClientDao clientdao = ClientDao.getInstance(test);
	
	public void checkAge(Client client) throws ServiceException {
		//Verifie l'age du client
		long age = ChronoUnit.YEARS.between(client.getNaissance().toLocalDate(), LocalDate.now());
		if (age < 18) {
			throw new ServiceException("Le client doit avoir 18 ans");
		}
	}

	public void checkMail(Client client) throws ServiceException {
		// Vérifier l'unicité du mail
		String mail = client.getEmail();
		List<Client> clients;
		try {
			clients = clientdao.findAll();
			for(int i=0; i<clients.size();i++) {
				if(mail.contentEquals(clients.get(i).getEmail())) {
					throw new ServiceException("L'adresse mail existe déjà");
				}
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void checkNom(Client client) throws ServiceException{
		// Vérifier la longueur du nom
		String nom = client.getNom();
		if(nom.length() < 3) {
			throw new ServiceException("Le nom doit contenir au moins 3 caractères");
		}
	}

	public void checkPrenom(Client client) throws ServiceException{
		// Vérifier la longueur du prénom
		String prenom = client.getPrenom();
		if(prenom.length() < 3) {
			throw new ServiceException("Le prénom doit contenir au moins 3 caractères");
		}
	}
	
	
	
	
	
}
