package com.ensta.rentmanager.service;


import java.util.List;
import java.util.Optional;

import com.ensta.rentmanager.classes.Client;
import com.ensta.rentmanager.dao.ClientDao;
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.validator.ClientValidator;
import com.ensta.rentmanager.exception.DaoException;

public class ClientService {
	private boolean test;
	private static ClientService instance = null;
	private static ClientService instanceTest = null;
	private ClientService() {}
	private ClientService(boolean test) {
		this.test = test;
	}
	public static ClientService getInstance(boolean test) {
		if(test) {
		if(instance == null) {
			instance = new ClientService(true);
		}
		return instanceTest;
		} else {
			if(instance == null) {
				instance = new ClientService(false);
			}
		}
		return instance;
	}
	public static ClientService getInstance() {
		return getInstance(false);
	}

ClientDao clientdao = ClientDao.getInstance(test);
ClientValidator clientvalidator = ClientValidator.getInstance(test);

public List<Client> findAll() throws ServiceException{
	try {
		return clientdao.findAll();
	}catch (DaoException e) {
		throw new ServiceException(e.getMessage());
	}
}

public Optional<Client> findById(long id) {
	return clientdao.findById(id);
}


public void update(Client client) throws ServiceException {
	clientvalidator.checkAge(client);
	clientvalidator.checkMail(client);
	clientvalidator.checkNom(client);
	clientvalidator.checkPrenom(client);
	try {
		clientdao.update(client);
	} catch (DaoException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


public long create(Client client) throws ServiceException {
	clientvalidator.checkAge(client);
	clientvalidator.checkMail(client);
	clientvalidator.checkNom(client);
	clientvalidator.checkPrenom(client);
	try {
		return clientdao.create(client);
	} catch(DaoException e) {
		throw new ServiceException(e.getMessage());
	}
}

public void delete(Client client) throws ServiceException{
	try {
		clientdao.delete(client);
	} catch(DaoException e) {
		throw new ServiceException(e.getMessage());
	}
}
	
	
}
