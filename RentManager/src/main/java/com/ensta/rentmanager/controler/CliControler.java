package com.ensta.rentmanager.controler;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.ensta.rentmanager.classes.Client;
import com.ensta.rentmanager.classes.Reservation;
import com.ensta.rentmanager.classes.Vehicle;
import com.ensta.rentmanager.exception.DaoException;
import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.service.ClientService;
import com.ensta.rentmanager.service.ReservationService;
import com.ensta.rentmanager.service.VehicleService;

public class CliControler {
	ClientService clientservice = ClientService.getInstance();
	VehicleService vehicleservice = VehicleService.getInstance();
	ReservationService reservationservice = ReservationService.getInstance();
	
	public static void main(String[] args) {
		CliControler cli = new CliControler();
		Scanner sc = new Scanner(System.in);
		boolean done = false ;
		while(!done) {
			System.out.println("Liste des opérations");
			System.out.println("1 - Affiche la liste des clients");
			System.out.println("2 - Ajoute un client");
			System.out.println("3 - Supprime un client");
			System.out.println("4 - Recherche un client");
			System.out.println("5 - Affiche la liste des véhicules");
			System.out.println("6 - Ajoute un véhicule");
			System.out.println("7 - Supprime un véhicule");
			System.out.println("8 - Recherche un véhicule");
			System.out.println("9 - Affiche la liste des réservations");
			System.out.println("10 - Ajoute une réservation");
			System.out.println("11 - Supprime une réservation");
			System.out.println("12 - Recherche les réservations d'un client");
			System.out.println("13 - Recherche les réservations d'un véhicule");
			System.out.println("14 - Recherche une réservation");
			System.out.println("15 - Modifier les informations d'un client");
			System.out.println("16 - Modifier les informations d'un véhicule");
			System.out.println("17 - Modifier les informations d'une réservation");
			int choix = sc.nextInt();
			sc.nextLine();
			switch(choix) {
			case 0:
				done = true ;
				break;
			case 1: 
				printAllClients(cli);
				break;
			case 2:
				addClient(cli, sc);
				break;
			case 3:
				deleteClient(cli, sc);
				break;
			case 4:
				printOneClient(cli, sc);
				break;
			case 5:
				printAllVehicles(cli);
				break;
			case 6:
				addVehicle(cli, sc);
				break;
			case 7:
				deleteVehicle(cli, sc);
				break;
			case 8:
				printOneVehicle(cli, sc);
				break;
			case 9:
				printAllReservations(cli);
				break;
			case 10:
				addReservation(cli, sc);
					break;
			case 11:
				deleteReservation(cli, sc);
				break;
			case 12:
				printResaClient(cli, sc);
				break;
			case 13:
				printResaVehicle(cli, sc);
				break;
			case 14:
				printOneResa(cli, sc);
				break;
			case 15:
				updateClient(cli, sc);
				break;
			case 16:
				updateVehicle(cli, sc);
				break;
			case 17:
				updateReservation(cli, sc);
				break;
			default:
				System.out.println("Pas le bon choix");
			}
		}
		
		
		sc.close();
		
	}

	private static void updateReservation(CliControler cli, Scanner sc) {
		System.out.println("Quel est l'id de la réservation à modifier ?");
		Reservation reservation = new Reservation();
		reservation.setId(sc.nextInt());
		System.out.println("Entrez l'id du client");
		sc.nextLine();
		reservation.setClient_id(sc.nextInt());
		System.out.println("Entrez l'id du véhicule");
		reservation.setVehicle_id(sc.nextInt());
		System.out.println("Entrez la date de début de réservation");
		sc.nextLine();
		reservation.setDebut(Date.valueOf(sc.nextLine()));
		System.out.println("Entrez la date de fin de réservation");
		reservation.setFin(Date.valueOf(sc.nextLine()));
		try {
		cli.reservationservice.update(reservation);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void updateVehicle(CliControler cli, Scanner sc) {
		System.out.println("Quel est l'id du véhicule à modifier ?");
		Vehicle vehicle = new Vehicle();
		vehicle.setId(sc.nextInt());
		System.out.println("Entrez le constructeur");
		sc.nextLine();
		vehicle.setConstructeur(sc.nextLine());
		System.out.println("Entrez le modèle");
		vehicle.setModele(sc.nextLine());
		System.out.println("Entrez le nombre de places");
		vehicle.setNb_places(sc.nextInt());
		try {
		cli.vehicleservice.update(vehicle);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void updateClient(CliControler cli, Scanner sc) {
		System.out.println("Quel est l'id du client à modifier ?");
		Client client = new Client();
		client.setId(sc.nextInt());
		System.out.println("Entrez le nom");
		sc.nextLine();
		client.setNom(sc.nextLine());
		System.out.println("Entrez le prénom");
		client.setPrenom(sc.nextLine());
		System.out.println("Entrez l'email");
		client.setEmail(sc.nextLine());
		System.out.println("Entrez la date de naissance au format yyyy-[m]m-[d]d");
		client.setNaissance(Date.valueOf(sc.nextLine()));
		try {
		cli.clientservice.update(client);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void printOneResa(CliControler cli, Scanner sc) {
		System.out.println("Quel est l'id de la réservation à rechercher ?");		
		System.out.println(cli.reservationservice.findResaById(sc.nextInt()));
	}

	private static void printResaVehicle(CliControler cli, Scanner sc) {
		System.out.println("Quel est l'id du véhicule dont vous souhaitez voir les réservations ?");		
		try {
			System.out.println(cli.reservationservice.findResaByVehicleId(sc.nextInt()));
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void printResaClient(CliControler cli, Scanner sc) {
		System.out.println("Quel est l'id du client dont vous souhaitez voir les réservations ?");		
		try {
			System.out.println(cli.reservationservice.findResaByClientId(sc.nextInt()));
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void deleteReservation(CliControler cli, Scanner sc) {
		try {
			System.out.println("Quel est l'id de la réservation à supprimer ?");
			int id = sc.nextInt();
			List<Reservation> list = cli.reservationservice.findAll() ;
			Reservation r = new Reservation();
			for(Reservation reservation : list) {
				if (reservation.getId()==id) {
					r = reservation ;
				}
			}
			cli.reservationservice.delete(r);
			} catch(ServiceException e) {
				e.printStackTrace();
			}
	}

	private static void addReservation(CliControler cli, Scanner sc) {
		Reservation reservation = new Reservation();
		System.out.println("Entrez l'id du client");
		reservation.setClient_id(sc.nextInt());
		System.out.println("Entrez l'id du véhicule");
		reservation.setVehicle_id(sc.nextInt());
		System.out.println("Entrez la date de début de la réservation au format yyyy-[m]m-[d]d");
		sc.nextLine(); 
		reservation.setDebut(Date.valueOf(sc.nextLine()));
		System.out.println("Entrez la date de fin de la réservation au format yyyy-[m]m-[d]d");
		reservation.setFin(Date.valueOf(sc.nextLine()));
		try {
		cli.reservationservice.create(reservation);
		} catch(ServiceException e) {
			e.printStackTrace();
		}
	}

	private static void printAllReservations(CliControler cli) {
		try  {
			List<Reservation> list = cli.reservationservice.findAll() ;
			for(Reservation reservation : list) {
				System.out.println(reservation);
			}
			} catch(ServiceException e) {
				System.out.println("Une erreur est survenue:" + e.getMessage());
			}
	}

	private static void printOneVehicle(CliControler cli, Scanner sc) {
		System.out.println("Quel est l'id du véhicule à rechercher ?");		
		System.out.println(cli.vehicleservice.findById(sc.nextInt()));
	}

	private static void printOneClient(CliControler cli, Scanner sc) {
		System.out.println("Quel est l'id du client à rechercher ?");		
		System.out.println(cli.clientservice.findById(sc.nextInt()));
	}

	private static void deleteVehicle(CliControler cli, Scanner sc) {
		try {
			System.out.println("Quel est l'id du véhicule à supprimer ?");
			int id = sc.nextInt();
			List<Vehicle> list = cli.vehicleservice.findAll() ;
			Vehicle v = new Vehicle();
			for(Vehicle vehicle : list) {
				if (vehicle.getId()==id) {
					v = vehicle ;
				}
			}
			cli.vehicleservice.delete(v);
			} catch(ServiceException e) {
				e.printStackTrace();
			}
	}

	private static void deleteClient(CliControler cli, Scanner sc) {
		try {
		System.out.println("Quel est l'id du client à supprimer ?");
		int id = sc.nextInt();
		List<Client> list = cli.clientservice.findAll() ;
		Client c = new Client();
		for(Client client : list) {
			if (client.getId()==id) {
				c = client ;
			}
		}
		cli.clientservice.delete(c);
		} catch(ServiceException e) {
			e.printStackTrace();
		}
	}

	private static void addVehicle(CliControler cli, Scanner sc) {
		Vehicle vehicle = new Vehicle();
		System.out.println("Entrez le constructeur");
		vehicle.setConstructeur(sc.nextLine());
		System.out.println("Entrez le modèle");
		vehicle.setModele(sc.nextLine());
		System.out.println("Entrez le nombre de places");
		vehicle.setNb_places(sc.nextInt());
		try {
		cli.vehicleservice.create(vehicle);
		} catch(ServiceException e) {
			e.printStackTrace();
		}
	}

	private static void printAllVehicles(CliControler cli) {
		try  {
			List<Vehicle> list = cli.vehicleservice.findAll() ;
			for(Vehicle vehicle : list) {
				System.out.println(vehicle);
			}
			} catch(ServiceException e) {
				System.out.println("Une erreur est survenue:" + e.getMessage());
			}
	}

	private static void addClient(CliControler cli, Scanner sc) {
		Client client = new Client();
		System.out.println("Entrez le nom");
		client.setNom(sc.nextLine());
		System.out.println("Entrez le prénom");
		client.setPrenom(sc.nextLine());
		System.out.println("Entrez l'email");
		client.setEmail(sc.nextLine());
		System.out.println("Entrez la date de naissance au format yyyy-[m]m-[d]d");
		client.setNaissance(Date.valueOf(sc.nextLine()));
		try {
		cli.clientservice.create(client);
		} catch(ServiceException e) {
			e.printStackTrace();
		}
	}

	private static void printAllClients(CliControler cli) {
		try  {
		List<Client> list = cli.clientservice.findAll() ;
		for(Client client : list) {
			System.out.println(client);
		}
		} catch(ServiceException e) {
			System.out.println("Une erreur est survenue:" + e.getMessage());
		}
	}
}
