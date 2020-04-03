package com.ensta.rentmanager.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ensta.rentmanager.classes.Reservation;
import com.ensta.rentmanager.dao.ReservationDao;
import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.exception.ServiceException;

public class ReservationValidator {
	private boolean test;
	private static ReservationValidator instance = null;
	private static ReservationValidator instanceTest = null;
	private ReservationValidator() {}
	private ReservationValidator(boolean test) {
		this.test = test;
	}
	public static ReservationValidator getInstance(boolean test) {
		if(test) {
		if(instance == null) {
			instance = new ReservationValidator(true);
		}
		return instanceTest;
		} else {
			if(instance == null) {
				instance = new ReservationValidator(false);
			}
		}
		return instance;
	}
	public static ReservationValidator getInstance() {
		return getInstance(false);
	}
	
	
	ReservationDao reservationdao = ReservationDao.getInstance(test);
	

	public void checkDuree(Reservation reservation) throws ServiceException{
		// Vérifier la durée de la réservation 
		Date debut = reservation.getDebut();
		Date fin = reservation.getFin();
		System.out.println(fin.getTime());
		System.out.println(debut.getTime());
		long diff = fin.getTime() - debut.getTime();
		System.out.println(diff);
		float res = (diff / (1000*60*60*24));
		System.out.println(res);
		if(res>7) {
			throw new ServiceException("La réservation ne peut pas excéder 7 jours");
		}
	}
	
	public void checkDispo(Reservation reservation) throws ServiceException{
		// Vérifier la disponibilité du véhicule
		int carId = reservation.getVehicle_id();
		Date debut = reservation.getDebut();
		Date fin = reservation.getFin();
		try {
			List<Reservation> resaAll = reservationdao.findAll();
			for(int i=0;i<resaAll.size();i++) {
				if(resaAll.get(i).getVehicle_id()==carId) {
					Date d = resaAll.get(i).getDebut();
					Date f = resaAll.get(i).getFin();
					if((d.getTime()-fin.getTime()) < 0  && (debut.getTime() - f.getTime()) < 0 ) {
						throw new ServiceException("Le véhicule est déjà réservé sur cette période");
					}
				}
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void checkPause(Reservation reservation) throws ServiceException {
		// Vérifier que le véhicule n'a pas été réservé plus de 30 jours sans pause
		int idCar = reservation.getVehicle_id();
		Date debut = reservation.getDebut();
		Date fin = reservation.getFin();
		float duree = (fin.getTime()-debut.getTime())/(1000*60*60*24) ;
		float duree2 = 0;
		try {
			List<Reservation> resa = reservationdao.findResaByVehicleId(idCar);
			ArrayList<Reservation> resa2 = new ArrayList<Reservation>() ;
			while(duree!=duree2) {
				duree2 = duree ;
			for(int i=0; i<resa.size(); i++) {
				System.out.println("duree" + duree);
				System.out.println("duree2" + duree2);
				long diff = debut.getTime() - resa.get(i).getFin().getTime();
				long diff2 = resa.get(i).getDebut().getTime() - fin.getTime();
				float res = (diff / (1000*60*60*24));
				float res2 = (diff2 / (1000*60*60*24));
				System.out.println(res);
				if(res ==1) {
					duree +=  (resa.get(i).getFin().getTime()-resa.get(i).getDebut().getTime())/(1000*60*60*24) ;
					System.out.println(duree);
					debut = resa.get(i).getDebut();
				}
				else if(res2 == 1 ) {
					duree +=  (resa.get(i).getFin().getTime()-resa.get(i).getDebut().getTime())/(1000*60*60*24) ;
					System.out.println(duree);
					fin = resa.get(i).getFin();
				}
				else {
					resa2.add(resa.get(i));
				}
			}
			resa = resa2 ;
			}
			if(duree>30) {
				throw new ServiceException("Le véhicule ne peut pas être réservé plus de 30 jours sans pause");
			}
			  
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
