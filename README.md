Management of vehicles' reservations

You can add, delete, update a client. You can also see a client's details : the number of vehicle he booked, the number of reservation he made and the details of those vehicles and reservations.
Moreover, you can add, delete and update a vehicle or a reservation. As for the clients, you can display the details of the clients/vehicles/reservations associated with the object.



Prerequisites

In order to run the program, you need Java 8, Eclipse JEE and Tomcat 9.



How to run project

In order to run the project, you can use the tomcat server. 
Commands : Run as ; Run on Server ; Select Tomcat.



The project 

When you run the project, the first page is a homepage. You can access the list of clients, the list of vehicles or the list of reservation with the side menu.
Each list proposes buttons to add, delete, update or see details of one instance of the class.
For creating and updating a client, a vehicle or a reservation, error messages can appear if conditions are not respected (conditions can be found in validator package).

Bonus tests are missing.



The project respects the MVC architecture with Dao package and Service package. 
Every function can be tested in CliControler with console.



Built With
    Maven - Dependency Management



Author 
Elise JOUSSELIN _ MIN 2021 _ 04/2020