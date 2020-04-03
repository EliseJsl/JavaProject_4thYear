package com.ensta.rentmanager.validator;

import com.ensta.rentmanager.classes.Vehicle;
import com.ensta.rentmanager.exception.ServiceException;

public class VehicleValidator {
	private boolean test;
	private static VehicleValidator instance = null;
	private static VehicleValidator instanceTest = null;
	private VehicleValidator() {}
	private VehicleValidator(boolean test) {
		this.test = test;
	}
	public static VehicleValidator getInstance(boolean test) {
		if(test) {
		if(instance == null) {
			instance = new VehicleValidator(true);
		}
		return instanceTest;
		} else {
			if(instance == null) {
				instance = new VehicleValidator(false);
			}
		}
		return instance;
	}
	public static VehicleValidator getInstance() {
		return getInstance(false);
	}
	
	public void checkSeat(Vehicle vehicle) throws ServiceException {
		//Verifie le nombre de sièges
		int seat = vehicle.getNb_places();
		if (seat<2 || seat>9) {
			throw new ServiceException("Le nombre de siège doit être compris entre 2 et 9");
		}
	}
}
