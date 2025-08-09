The "YO"-Server:

The "YO"-Server is a Spring-Boot project that includes the main principles of a service-oriented backend structure. 

It is capable of creating and managing users that are able to communicate using the Rest API, by sending "Yo's".
"Yo's" are, in this case, placeholders for whatever needs to be communicated. For example a message or some other form of data.
The application is divided by the Rest-Controller, the Services, the Repository and a SQL-Database wich communicate in this order.
The Rest-Controller manages the API, while the services provide the functional layer of all post- and get-requests. The Repository serves as a interface between services and Database.

The application still has a lot of security issues beginning with the fact it is running on http and not https.
Also the application is stateless wich means there is no sessionhandling.
This means that userpasswords, for example need to be send everytime an action is commited.
The application has proper passwordhashing.

Unit tests that test the Service-Structure are also addet.
Still an integration test is missing.

All self programmed sercive, controller and data class files are under blog\src\main\kotlin\com\example\blog.

All other necessary files to run the application are provided
