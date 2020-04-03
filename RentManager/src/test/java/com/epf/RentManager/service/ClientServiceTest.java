package com.epf.RentManager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Date;

import org.junit.Test;

import com.ensta.rentmanager.classes.Client;
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.service.ClientService;

public class ClientServiceTest {
	
	ClientService clientService = ClientService.getInstance();
	@Test
	public void testCreate(){
		Client client = new Client();
		client.setNom("nom");
		client.setPrenom("prenom");
		client.setEmail("email@email");
		client.setNaissance(Date.valueOf("1998-05-07"));
		
		try {
			clientService.create(client);
		} catch(ServiceException e) {
			fail();
		}
	}
	
	@Test(expected = ServiceException.class)
	public void testCreateFail() throws ServiceException{
		Client client = new Client();
		client.setNom("nom");
		client.setPrenom("prenom");
		client.setEmail("email@email");
		client.setNaissance(Date.valueOf("2019-05-07"));
		clientService.create(client);
	}
	
	@Test
	public void quelquesFonctionPratiques() {
		assertEquals("Ils ne sont pas égaux", new Long(2), new Long(2));
		assertTrue("Ce n'est pas vrai", true);
	}

}
