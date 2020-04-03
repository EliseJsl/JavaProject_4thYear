package com.ensta.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.classes.Client;
import com.ensta.rentmanager.classes.Vehicle;
import com.ensta.rentmanager.persistence.ConnectionManager;

public class VehicleDao {
	private boolean test;
	private static VehicleDao instance = null;
	private static VehicleDao instanceTest = null;
	private VehicleDao() {}
	private VehicleDao(boolean test) {
		this.test = test;
	}
	public static VehicleDao getInstance(boolean test) {
		if(test) {
		if(instance == null) {
			instance = new VehicleDao(true);
		}
		return instanceTest;
		} else {
			if(instance == null) {
				instance = new VehicleDao(false);
			}
		}
		return instance;
	}
	
	public static VehicleDao getInstance() {
		return getInstance(false);
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur=?, modele=?, nb_places=? WHERE id=?;";
	
	public long create(Vehicle vehicle) throws DaoException {
		try (Connection conn = test ? ConnectionManager.getConnectionForTest(): ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(CREATE_VEHICLE_QUERY);) {
				
				statement.setString(1,vehicle.getConstructeur());
				statement.setString(2, vehicle.getModele());
				statement.setInt(3,vehicle.getNb_places());
				
				long result = statement.executeUpdate();
				return result ;
			} catch (SQLException e) {
				throw new DaoException("Erreur lors de la création : " + e.getMessage());
			}
	}

	public void delete(Vehicle vehicle) throws DaoException {
		try(Connection conn = test ? ConnectionManager.getConnectionForTest(): ConnectionManager.getConnection();
	PreparedStatement statement = conn.prepareStatement(DELETE_VEHICLE_QUERY);){
			long id = vehicle.getId();
			statement.setLong(1,id);
			statement.executeUpdate();
		} catch(SQLException e) {
			System.out.println("Erreur lors du select " + e.getMessage());
			
		}
	}


	public List<Vehicle> findAll() throws DaoException {
		 List<Vehicle> resultList = new ArrayList<>();
		 try(Connection conn = test ? ConnectionManager.getConnectionForTest(): ConnectionManager.getConnection();
		PreparedStatement statement = conn.prepareStatement(FIND_VEHICLES_QUERY);){
			 ResultSet resultSet = statement.executeQuery();
			 while(resultSet.next()) {
			 // facon 1
			 Vehicle vehicle = new Vehicle(resultSet.getInt(1),
					 					resultSet.getString(2),
					 					resultSet.getString(3),
					 					resultSet.getInt(4));
			 resultList.add(vehicle);
			 //facon 2
			 //Vehicle vehicle2 = new Vehicle();
			 //vehicle2.setId(resultSet.getInt(1));
			 //vehicle2.setConstructeur(resultSet.getString(2));
			 //vehicle2.setModele(resultSet.getString(3));
			 //vehicle2.setNb_places(resultSet.getInt(4));
			 //resultList.add(vehicle2);
			 }
			 
		 }catch (SQLException e) {
				throw new DaoException("Erreur lors de la création : " + e.getMessage());
			} 
		 return resultList ;
	}
	
	
	public static void main (String... args) throws DaoException {
		VehicleDao dao = VehicleDao.getInstance();
		try {
			List<Vehicle> list = dao.findAll();
			for (Vehicle v : list) { // for each
				System.out.println(v);
			}
		}catch (DaoException e) {
			System.out.println("Erreur lors du select " + e.getMessage());
		} 
		Optional<Vehicle> optVehicle = dao.findById(3);
		
		if(optVehicle.isPresent()) {
			Vehicle v = optVehicle.get();
			System.out.println(v);
		}else {
			System.out.println("Erreur lors du select du client");
		}
		
}


public Optional<Vehicle> findById(long id) {
	Optional<Vehicle> optVehicle ;
	try(Connection conn = test ? ConnectionManager.getConnectionForTest(): ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(FIND_VEHICLE_QUERY);) {
	statement.setLong(1,id);
	ResultSet resultSet = statement.executeQuery();
	resultSet.next();
	Vehicle vehicle = new Vehicle((int)id,
				resultSet.getString(1),
				resultSet.getString(2),
				resultSet.getInt(3));
	optVehicle = Optional.of(vehicle);
	
	}
	catch (SQLException e) {
		System.out.println("Erreur lors du select " + e.getMessage());
		return Optional.empty();
	}
	return optVehicle ;
	
}

public void update(Vehicle vehicle) throws DaoException {
	
	try(Connection conn = test ? ConnectionManager.getConnectionForTest(): ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(UPDATE_VEHICLE_QUERY);) {
	
	statement.setString(1,vehicle.getConstructeur());
	statement.setString(2, vehicle.getModele());
	statement.setInt(3, vehicle.getNb_places());
	statement.setInt(4, vehicle.getId());
	
	statement.executeUpdate();
	
	}
	catch (SQLException e) {
		System.out.println("Erreur lors du select " + e.getMessage());
	}
	 
}

}
