package com.ensta.rentmanager.service;

import java.util.List;
import java.util.Optional;

import com.ensta.rentmanager.classes.Vehicle;
import com.ensta.rentmanager.dao.VehicleDao;
import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.validator.VehicleValidator;

public class VehicleService {
	private boolean test;
	private static VehicleService instance = null;
	private static VehicleService instanceTest = null;
	private VehicleService() {}
	private VehicleService(boolean test) {
		this.test = test;
	}
	public static VehicleService getInstance(boolean test) {
		if(test) {
		if(instance == null) {
			instance = new VehicleService(true);
		}
		return instanceTest;
		} else {
			if(instance == null) {
				instance = new VehicleService(false);
			}
		}
		return instance;
	}
	public static VehicleService getInstance() {
		return getInstance(false);
	}
	

VehicleDao vehicledao = VehicleDao.getInstance(test);
VehicleValidator vehiclevalidator = VehicleValidator.getInstance(test);

public List<Vehicle> findAll() throws ServiceException{
	try {
		return vehicledao.findAll();
	}catch (DaoException e) {
		throw new ServiceException(e.getMessage());
	}
}

public Optional<Vehicle> findById(long id) {
	return vehicledao.findById(id);
}

public long create(Vehicle vehicle) throws ServiceException {
	vehiclevalidator.checkSeat(vehicle);
	try {
		return vehicledao.create(vehicle);
	} catch(DaoException e) {
		throw new ServiceException(e.getMessage());
	}
}


public void delete(Vehicle vehicle) throws ServiceException {
	try {
		vehicledao.delete(vehicle);
	} catch(DaoException e) {
		throw new ServiceException(e.getMessage());
	}
}

public void update(Vehicle vehicle) throws ServiceException  {
	vehiclevalidator.checkSeat(vehicle);
	try {
		vehicledao.update(vehicle);
	} catch (DaoException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}



}
