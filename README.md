# Introduction to Service Design And Engineering Assignment 3 Server
Submitted By: Bishruti Siku

This assignment is mainly focused on implementing services that follows the SOAP protocol. In this module, we will get acquainted with implementing SOAP web services using JAX-WS, adding JPA to access databases. In this project I have implemented the server side of the SOAP application. This application stores data in database using SQLite and performs various operations like `read`, `write`, `update` and `delete`. This project is deployed in Heroku and can be accessed from [Heroku Link](https://introsde-assignment3-ehealth.herokuapp.com/ws/people?wsdl). Furthermore, the client application for this server application is implemented in [this project](https://github.com/Bishruti/introsde-2015-assignment-3-client).

#### Structure

In the root file I have the following files.

**Source Folder (src)**

Possess source code files utilized in this application.

*DAO*

DAO stands for Data Access Objects and files in this folder are responsible to provide datas. It provides Object Relational Mapping (ORM) to map the models into database. In this project we are using
`LifeCoachDao.java`.

*ENDPOINT*

Responsible to establish server and to create an endpoint publisher. `PeoplePublisher.java` establish the server for this project.

*MODEL*

In this folder we have java classes which define the database as well as various operations that we can perform in our database. This project mainly consists of `HealthMeasureHistory.java`, `HealthProfile.java`, `MeasureTypes.java` and `Person.java`.

*WEB SERVICES (WS)*

This folder consists of the java classes which are responsible to implement the body of the SOAP message. `People.java` is responsible to perform various operations like `read`, `write`, `update` and `delete` as well as pass the parameters for querying and give the output in appropriate parameters. Similarly, `PeopleImpl.java` is responsible to implement various opertaions indicated in `People.java`


*META-INF*

Consists of the files that are required for setup.

  1. `persistence.xml`

      Required to generate the database from the models.

  2. `MANIFEST.MF`

      Indicates the Manifest-Version.

*Procfile*

Required to run command in Heroku.

*build.xml*

It is a low-level mechanism to package. It compiles and archives source code into a single `jar` file.

*ivy.xml*

Contains description of the dependencies of a module, its published artifacts and its configurations.

*ehealth-soap.sqlite*

Database for the project.

#### Supported Database Queries.

`Method #1: readPersonList() => List` 

Obtains all the people and their details in the list.

`Method #2: readPerson(Long id) => Person`

Obtains a person and the details associated to that person from the list.

`Method #3: updatePerson(Person p) => Person`

Edits a person in the list.

`Method #4: createPerson(Person p) => Person`

Adds a new person in the list.

`Method #5: deletePerson(Long id) => Deleted Person Id`

Deletes a person from the list.

`Method #6: readPersonHistory(Long id, String measureType) => List`

Obtains all measure details about a measure of a person in the list.

`Method #7: readMeasureTypes() => List`

Obtains all measures in the list.

`Method #8: readPersonMeasure(Long id, String measureType, Long mid) => Measure`

Obtains measure details about a particular measure of a person in the list.

`Method #9: savePersonMeasure(Long id, Measure m) => Person`

Creates measure details about a measure of a person in the list.

`Method #10: updatePersonMeasure(Long id, Measure m) => Measure`

Updates measure details about a measure of a person in the list.

#### Program Execution

To Execute Server:

1. Open the terminal.

2. Go to the root directory of the program.

3. Run `ant start`
