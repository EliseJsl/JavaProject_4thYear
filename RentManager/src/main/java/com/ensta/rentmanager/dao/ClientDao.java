package com.ensta.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.classes.Client;
// import com.ensta.rentmanager.model.Vehicle;
import com.ensta.rentmanager.persistence.ConnectionManager;


public class ClientDao {
	private boolean test;
	private static ClientDao instance = null;
	private static ClientDao instanceTest = null;
	private ClientDao() {}
	private ClientDao(boolean test) {
		this.test = test;
	}
	public static ClientDao getInstance(boolean test) {
		if(test) {
		if(instance == null) {
			instance = new ClientDao(true);
		}
		return instanceTest;
		} else {
			if(instance == null) {
				instance = new ClientDao(false);
			}
		}
		return instance;
	}
	
	public static ClientDao getInstance() {
		return getInstance(false);
	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom=?, prenom=?, email=?, naissance=? WHERE id=?;";
	

	public long create(Client client) throws DaoException {
	try (Connection conn = test ? ConnectionManager.getConnectionForTest(): ConnectionManager.getConnection();
		PreparedStatement statement = conn.prepareStatement(CREATE_CLIENT_QUERY);) {
		
		statement.setString(1,client.getNom());
		statement.setString(2,client.getPrenom());
		statement.setString(3, client.getEmail());
		statement.setDate(4,client.getNaissance());
		
		long result = statement.executeUpdate();
		return result ;
	} catch (SQLException e) {
		throw new DaoException("Erreur lors de la création : " + e.getMessage());
	} 
	}
	
	
	public void delete (Client client) throws DaoException {
		try(Connection conn = test ? ConnectionManager.getConnectionForTest(): ConnectionManager.getConnection();
		PreparedStatement statement = conn.prepareStatement(DELETE_CLIENT_QUERY);){
		long id = client.getId();
		statement.setLong(1,id);
		statement.executeUpdate();
		} catch(SQLException e) {
				System.out.println("Erreur lors du select " + e.getMessage());
			}
		}
	

	public List<Client> findAll() throws DaoException {
		 List<Client> resultList = new ArrayList<>();
		 try(Connection conn = test ? ConnectionManager.getConnectionForTest(): ConnectionManager.getConnection();
		PreparedStatement statement = conn.prepareStatement(FIND_CLIENTS_QUERY);){
			 ResultSet resultSet = statement.executeQuery();
			 while(resultSet.next()) {
			 // facon 1
			 Client client = new Client(resultSet.getInt(1),
					 					resultSet.getString(2),
					 					resultSet.getString(3),
					 					resultSet.getString(4),
					 					resultSet.getDate(5));
			 resultList.add(client);
			 //facon 2
			 //Client client2 = new Client();
			 //client2.setId(resultSet.getInt(1));
			 //client2.setNom(resultSet.getString(2));
			 //client2.setPrenom(resultSet.getString(3));
			 //client2.setEmail(resultSet.getString(4));
			 //client2.setNaissance(resultSet.getDate(5));
			 //resultList.add(client2);
			 }
			 
		 }catch (SQLException e) {
				throw new DaoException("Erreur lors de la création : " + e.getMessage());
			} 
		 return resultList ;
	}

	
public static void main (String... args) throws DaoException {
		ClientDao dao = ClientDao.getInstance();
		try {
			List<Client> list = dao.findAll();
			for (Client c : list) { // for each
				System.out.println(c);
			}
		}catch (DaoException e) {
			System.out.println("Erreur lors du select " + e.getMessage());
		} 
		Optional<Client> optClient = dao.findById(3);
		
		if(optClient.isPresent()) {
			Client c = optClient.get();
			System.out.println(c);
		}else {
			System.out.println("Erreur lors du select du client");
		}
	
}


public Optional<Client> findById(long id) {
	Optional<Client> optClient ;
	try(Connection conn = test ? ConnectionManager.getConnectionForTest(): ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(FIND_CLIENT_QUERY);) {
	statement.setLong(1,id);
	ResultSet resultSet = statement.executeQuery();
	resultSet.next();
	Client client = new Client((int)id,
				resultSet.getString(1),
				resultSet.getString(2),
				resultSet.getString(3),
				resultSet.getDate(4));
	optClient = Optional.of(client);
	
	}
	catch (SQLException e) {
		System.out.println("Erreur lors du select " + e.getMessage());
		return Optional.empty();
	}
	return optClient ;
	
}

public void update(Client client) throws DaoException {
	
	try(Connection conn = test ? ConnectionManager.getConnectionForTest(): ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(UPDATE_CLIENT_QUERY);) {
	
	statement.setString(1,client.getNom());
	statement.setString(2, client.getPrenom());
	statement.setString(3, client.getEmail());
	statement.setDate(4, client.getNaissance());
	statement.setInt(5, client.getId());
	
	statement.executeUpdate();
	
	}
	catch (SQLException e) {
		System.out.println("Erreur lors du select " + e.getMessage());
	}
	 
}


}
