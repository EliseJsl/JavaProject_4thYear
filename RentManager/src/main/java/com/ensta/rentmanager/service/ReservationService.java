package com.ensta.rentmanager.service;

import java.util.List;
import java.util.Optional;

import com.ensta.rentmanager.classes.Reservation;
import com.ensta.rentmanager.dao.ReservationDao;
import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.validator.ReservationValidator;

public class ReservationService {
		private boolean test;
		private static ReservationService instance = null;
		private static ReservationService instanceTest = null;
		private ReservationService() {}
		private ReservationService(boolean test) {
			this.test = test;
		}
		
	
		public static ReservationService getInstance(boolean test) {
			if(test) {
			if(instance == null) {
				instance = new ReservationService(true);
			}
			return instanceTest;
			} else {
				if(instance == null) {
					instance = new ReservationService(false);
				}
			}
			return instance;
		}
		public static ReservationService getInstance() {
			return getInstance(false);
		}
	
		ReservationDao reservationdao = ReservationDao.getInstance(test);
		ReservationValidator reservationvalidator = ReservationValidator.getInstance(test);

		public List<Reservation> findAll() throws ServiceException{
			try {
				return reservationdao.findAll();
			}catch (DaoException e) {
				throw new ServiceException(e.getMessage());
			}
		}

		public List<Reservation> findResaByClientId(long id_client) throws DaoException {
			return reservationdao.findResaByClientId(id_client);
		}
		
		public List<Reservation> findResaByVehicleId(long id_vehicle) throws DaoException {
			return reservationdao.findResaByVehicleId(id_vehicle);
		}
		
		public Optional<Reservation> findResaById(long id) {
			return reservationdao.findById(id);
		}

		public long create(Reservation reservation) throws ServiceException {
			reservationvalidator.checkDuree(reservation);
			reservationvalidator.checkDispo(reservation);
			reservationvalidator.checkPause(reservation);
			try {
				return reservationdao.create(reservation);
			} catch(DaoException e) {
				throw new ServiceException(e.getMessage());
			}
		}
		

		public void delete(Reservation reservation) throws ServiceException {
			try {
				reservationdao.delete(reservation);
			} catch(DaoException e) {
				throw new ServiceException(e.getMessage());
			}
		}
		
		public void update(Reservation reservation) throws ServiceException {
			reservationvalidator.checkDuree(reservation);
			reservationvalidator.checkDispo(reservation);
			reservationvalidator.checkPause(reservation);
			try {
				reservationdao.update(reservation);
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}

