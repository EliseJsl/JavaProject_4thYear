package com.ensta.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.classes.Client;
import com.ensta.rentmanager.classes.Reservation;
import com.ensta.rentmanager.classes.Vehicle;
import com.ensta.rentmanager.persistence.ConnectionManager;


public class ReservationDao {
	private boolean test;
	private static ReservationDao instance = null;
	private static ReservationDao instanceTest = null;
	private ReservationDao() {}
	private ReservationDao(boolean test) {
		this.test = test;
	}
	public static ReservationDao getInstance(boolean test) {
		if(test) {
		if(instance == null) {
			instance = new ReservationDao(true);
		}
		return instanceTest;
		} else {
			if(instance == null) {
				instance = new ReservationDao(false);
			}
		}
		return instance;
	}
	
	public static ReservationDao getInstance() {
		return getInstance(false);
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String FIND_RESERVATION_BY_ID = "SELECT client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET client_id=?, vehicle_id=?, debut=?, fin=? WHERE id=?;";
		
	public long create(Reservation reservation) throws DaoException {
		try (Connection conn = test ? ConnectionManager.getConnectionForTest(): ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(CREATE_RESERVATION_QUERY);) {
				
				statement.setInt(1,reservation.getClient_id());
				statement.setInt(2,reservation.getVehicle_id());
				statement.setDate(3, reservation.getDebut());
				statement.setDate(4,reservation.getFin());
				
				long result = statement.executeUpdate();
				return result ;
			} catch (SQLException e) {
				throw new DaoException("Erreur lors de la création : " + e.getMessage());
			} 
	}
	
	public void delete(Reservation reservation) throws DaoException {
		try(Connection conn = test ? ConnectionManager.getConnectionForTest(): ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(DELETE_RESERVATION_QUERY);){
						long id = reservation.getId();
						statement.setLong(1,id);
						statement.executeUpdate();
					} catch(SQLException e) {
						System.out.println("Erreur lors du select " + e.getMessage());
						
					}
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> resultList = new ArrayList<>();
		try(Connection conn = test ? ConnectionManager.getConnectionForTest(): ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);) {
		statement.setLong(1,clientId);
		ResultSet resultSet = statement.executeQuery();
		while(resultSet.next()) {
			 // facon 1
			 Reservation reservation = new Reservation(resultSet.getInt(1),
					 					(int)clientId,
					 					resultSet.getInt(2),
					 					resultSet.getDate(3),
					 					resultSet.getDate(4));
			 resultList.add(reservation);
			 }
		
		
		}
		catch (SQLException e) {
			System.out.println("Erreur lors du select " + e.getMessage());
			
		}
		return resultList ;
	}
	

	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> resultList = new ArrayList<>();
		try(Connection conn = test ? ConnectionManager.getConnectionForTest(): ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);) {
		statement.setLong(1,vehicleId);
		ResultSet resultSet = statement.executeQuery();
		while(resultSet.next()) {
			 // facon 1
			 Reservation reservation = new Reservation(resultSet.getInt(1),
					 					resultSet.getInt(2),
					 					(int) vehicleId,
					 					resultSet.getDate(3),
					 					resultSet.getDate(4));
			 resultList.add(reservation);
			 }
		
		
		}
		catch (SQLException e) {
			System.out.println("Erreur lors du select " + e.getMessage());
			
		}
		return resultList ;
	}
	
	public Optional<Reservation> findById(long id) {
		Optional<Reservation> optReservation ;
		try(Connection conn = test ? ConnectionManager.getConnectionForTest(): ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(FIND_RESERVATION_BY_ID);) {
		statement.setLong(1,id);
		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		Reservation r = new Reservation((int)id,
					resultSet.getInt(1),
					resultSet.getInt(2),
					resultSet.getDate(3),
					resultSet.getDate(4));
		optReservation = Optional.of(r);
		
		}
		catch (SQLException e) {
			System.out.println("Erreur lors du select " + e.getMessage());
			return Optional.empty();
		}
		return optReservation ;
	}

	
	public List<Reservation> findAll() throws DaoException {
		List<Reservation> resultList = new ArrayList<>();
		 try(Connection conn = test ? ConnectionManager.getConnectionForTest(): ConnectionManager.getConnection();
		PreparedStatement statement = conn.prepareStatement(FIND_RESERVATIONS_QUERY);){
			 ResultSet resultSet = statement.executeQuery();
			 while(resultSet.next()) {
			 // facon 1
			 Reservation reservation = new Reservation(resultSet.getInt(1),
					 					resultSet.getInt(2),
					 					resultSet.getInt(3),
					 					resultSet.getDate(4),
					 					resultSet.getDate(5));
			 resultList.add(reservation);
			 }
			 
		 }catch (SQLException e) {
				throw new DaoException("Erreur lors de la création : " + e.getMessage());
			} 
		 return resultList ;
	}
	
	public void update(Reservation reservation) throws DaoException {
		
		try(Connection conn = test ? ConnectionManager.getConnectionForTest(): ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(UPDATE_RESERVATION_QUERY);) {
		
		statement.setInt(1,reservation.getClient_id());
		statement.setInt(2, reservation.getVehicle_id());
		statement.setDate(3, reservation.getDebut());
		statement.setDate(4, reservation.getFin());
		statement.setInt(5, reservation.getId());
		
		statement.executeUpdate();
		
		}
		catch (SQLException e) {
			System.out.println("Erreur lors du select " + e.getMessage());
		}
		 
	}
}
