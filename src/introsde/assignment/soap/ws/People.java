package introsde.assignment.soap.ws;

import introsde.assignment.soap.model.HealthMeasureHistory;
import introsde.assignment.soap.model.MeasureTypes;
import introsde.assignment.soap.model.Person;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import java.util.List;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) //optional
public interface People {

    /* Request 1
        Request to obtain all the people and their details in the list.
        Expected Input: -
        Expected Output: List of people (String) */

    @WebMethod(operationName="readPersonList")
    @WebResult(name="person")
    public List<Person> readPersonList();

    /* Request 2
        Request to obtain a person and the details associated to that person from the list.
        Expected Input: PersonId (Integer)
        Expected Output: Person and the details associated to that person. (String) */

    @WebMethod(operationName="readPerson")
    @WebResult(name="person")
    public Person readPerson(@WebParam(name="personId") int id);

    /* Request 3
        Request to edit a person in the list.
        Expected Input: PersonId (Integer) and Person (Object)
        Expected Output: Edited Person with the details associated to that person. (String) */

    @WebMethod(operationName="updatePerson")
    @WebResult(name="person")
    public Person updatePerson(@WebParam(name="person") Person person);

    /* Request 4
        Request to add a new person in the list.
        Expected Input: Person (Object)
        Expected Output: Newly created Person with the details associated to that person. (String) */

    @WebMethod(operationName="createPerson")
    @WebResult(name="person")
    public Person createPerson(Person person);

    /* Request 5
        Request to delete a person from the list.
        Expected Input: personId (Integer)
        Expected Output: Response Message. */

    @WebMethod(operationName="deletePerson")
    @WebResult(name="person")
    public int deletePerson(@WebParam(name="personId") int id);

    /* Request 6
       Request to obtain all measure details about a measure of a person in the list.
       Expected Input: personId (Integer)
                       measureType (String)
       Expected Output: List of measure types. (String) */

    @WebMethod(operationName="readPersonHistory")
    @WebResult(name="measure")
    public List<HealthMeasureHistory> readPersonHistory(@WebParam(name="personId") int id, @WebParam(name="measureType") String measureType);

    /* Request 7
     Request to obtain all measures in the list.
     Expected Input: -
     Expected Output: List of measures.  (String) */

    @WebMethod(operationName="readMeasureTypes")
    @WebResult(name="measureTypes")
    public MeasureTypes readMeasureTypes();

    /* Request 8
        Request to obtain measure details about a particular measure of a person in the list.
        Expected Input: personId (Integer)
                        measureType (String)
                        measureId (Integer)
        Expected Output: Details of a particular measure. (String) */

    @WebMethod(operationName="readPersonMeasure")
    @WebResult(name="measure")
    public List<HealthMeasureHistory> readPersonMeasure(@WebParam(name="personId") int id, @WebParam(name="measureType") String measureType, @WebParam(name="mid") int mid);

    /* Request 9
        Request to create measure details about a measure of a person in the list.
        Expected Input: personId (Integer)
        measureType (String)
        MeasureDetails (Object)
        Expected Output:
        List of newly created measure. (String) */

    @WebMethod(operationName="savePersonMeasure")
    @WebResult(name="measure")
    public HealthMeasureHistory savePersonMeasure(@WebParam(name="personId") int id, @WebParam(name="measure") HealthMeasureHistory healthMeasureHistory);

    /* Request 10
        Request to update measure details about a measure of a person in the list.
        Expected Input: personId (Integer)
        measureType (String)
        mId (Integer)
        MeasureDetails (Object)
        Expected Output:
        List of updated measure. (String) */

    @WebMethod(operationName="updatePersonMeasure")
    @WebResult(name="measure")
    public HealthMeasureHistory updatePersonMeasure(@WebParam(name="personId") int id, @WebParam(name="measure") HealthMeasureHistory healthMeasureHistory);
}