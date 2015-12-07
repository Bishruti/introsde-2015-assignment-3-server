package introsde.assignment.soap.ws;

import introsde.assignment.soap.model.HealthMeasureHistory;
import introsde.assignment.soap.model.HealthProfile;
import introsde.assignment.soap.model.MeasureTypes;
import introsde.assignment.soap.model.Person;

import javax.jws.WebService;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

//Service Implementation

@WebService(endpointInterface = "introsde.assignment.soap.ws.People",
        serviceName="PeopleService")

public class PeopleImpl implements People {

     /* Request 1
        Request to obtain all the people and their details in the list.
        Expected Input: -
        Expected Output: List of people (String) */

    @Override
    public List<Person> readPersonList() {
        System.out.println("Reading the list of People");
        return Person.getAll();
    }

    /* Request 2
       Request to obtain a person and the details associated to that person from the list.
       Expected Input: PersonId (Integer)
       Expected Output: Person and the details associated to that person. (String) */

    @Override
    public Person readPerson(int id) {
        System.out.println("Reading Person with id: " + id);
        Person person = Person.getPersonById(id);
        if (person!=null) {
            System.out.println("Successfully found Person with id: " + id + " and name: " + person.getFirstname() + " " + person.getLastname());
        } else {
            System.out.println("Unable to find any Person with id: " + id);
        }
        return person;
    }

    /* Request 3
        Request to edit a person in the list.
        Expected Input: PersonId (Integer) and Person (Object)
        Expected Output: Edited Person with the details associated to that person. (String) */

    @Override
    public Person updatePerson(Person person) {
        int personId = person.getIdPerson();
        Person existing = Person.getPersonById(personId);
        if (existing == null) {
            System.out.println("Cannot find person with id: " + personId);
        } else {
            String updatedFirstName = person.getFirstname();
            String updatedLastName = person.getLastname();
            if (updatedFirstName != null) {
                existing.setFirstname(updatedFirstName);
            }
            if (updatedLastName != null) {
                existing.setLastname(updatedLastName);
            }
            if (person.getBirthdate() != null) {
                try {
                    existing.setBirthdate(person.getBirthdate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            Person.updatePerson(existing);
        }
        System.out.println("Successfully updated personal information of person wid id: " + personId);
        return existing;
    }

    /* Request 4
        Request to add a new person in the list.
        Expected Input: Person (Object)
        Expected Output: Newly created Person with the details associated to that person. (String) */

    @Override
    public Person createPerson(Person person) {
        Person.savePerson(person);
        System.out.println("Successfully created person.");
        return person;
    }

     /* Request 5
        Request to delete a person from the list.
        Expected Input: personId (Integer)
        Expected Output: Response Message. */

    @Override
    public int deletePerson(int id) {
        Person person = Person.getPersonById(id);
        if (person!=null) {
            Person.removePerson(person);
            System.out.println("Successfully deleted Person with id: " + id );
            return 0;
        } else {
            System.out.println("Cannot find person with id: " + id);
            return -1;
        }
    }

     /* Request 6
       Request to obtain all measure details about a measure of a person in the list.
       Expected Input: personId (Integer)
                       measureType (String)
       Expected Output: List of measure types. (String) */

    @Override
    public List<HealthMeasureHistory> readPersonHistory(int id, String measureType) {
        List<HealthMeasureHistory> healthMeasureHistory = HealthMeasureHistory.getHealthHistoryOfPersonByMeasureType(id, measureType);
        if (healthMeasureHistory == null)
            throw new RuntimeException("Get: Person with " + id + " not found");
        return healthMeasureHistory;
    }

    /* Request 7
      Request to obtain all measures in the list.
      Expected Input: -
      Expected Output: List of measures.  (String) */

    @Override
    public MeasureTypes readMeasureTypes() {
        System.out.println("Getting list of Measure Type...");
        MeasureTypes measureTypes = new MeasureTypes();
        measureTypes.setMeasureType(HealthMeasureHistory.getTypes());
        return measureTypes;
    }

    /* Request 8
        Request to obtain measure details about a particular measure of a person in the list.
        Expected Input: personId (Integer)
                        measureType (String)
                        measureId (Integer)
        Expected Output: Details of a particular measure. (String) */

    @Override
    public List<HealthMeasureHistory> readPersonMeasure(int id, String measureType, int mid) {
        List<HealthMeasureHistory> healthMeasureHistory = HealthMeasureHistory.getMeasurebyMid(id, measureType, mid);
        if (healthMeasureHistory == null)
            throw new RuntimeException("Get: Person with " + id + " not found");
        return healthMeasureHistory;
    }

    /* Request 9
        Request to create measure details about a measure of a person in the list.
        Expected Input: personId (Integer)
        measureType (String)
        MeasureDetails (Object)
        Expected Output:
        List of newly created measure. (String) */

    @Override
    public HealthMeasureHistory savePersonMeasure(int id, HealthMeasureHistory healthMeasureHistory) {
        System.out.println("Creating new Health Measure History...");
        Person person = Person.getPersonById(id);

        if (person == null) {
            System.out.println("Unable to find the person with id: " + id);
            return null;
        }
        else {
            List<HealthProfile> healthProfiles = person.getMeasureType();
            if (healthProfiles == null) {
                HealthProfile hp = new HealthProfile();
                hp.setMeasureType(healthMeasureHistory.getMeasureType());
                hp.setMeasureValue(healthMeasureHistory.getMeasureValue());
                hp.setMeasureValueType(healthMeasureHistory.getMeasureValueType());
                hp.setDateRegistered(new Date());
                hp.setPerson(person);
                HealthProfile.saveHealthProfile(hp);
            }
            else {
                for (HealthProfile healthProfile : healthProfiles) {
                    if (healthProfile.getMeasureType().equalsIgnoreCase(healthMeasureHistory.getMeasureType())) {
                        healthProfile.setMeasureValue(healthMeasureHistory.getMeasureValue());
                        healthProfile.setMeasureValueType(healthMeasureHistory.getMeasureValueType());
                        healthProfile.setDateRegistered(new Date());
                        HealthProfile.updateHealthProfile(healthProfile);
                    }
                }
            }
            healthMeasureHistory.setMeasureType(healthMeasureHistory.getMeasureType());
            healthMeasureHistory.setMeasureValue(healthMeasureHistory.getMeasureValue());
            healthMeasureHistory.setMeasureValueType(healthMeasureHistory.getMeasureValueType());
            healthMeasureHistory.setDateRegistered(new Date());
            healthMeasureHistory.setPerson(person);
            return HealthMeasureHistory.saveHealthMeasureHistory(healthMeasureHistory);
        }
    }

    /* Request 10
        Request to update measure details about a measure of a person in the list.
        Expected Input: personId (Integer)
        measureType (String)
        mId (Integer)
        MeasureDetails (Object)
        Expected Output:
        List of updated measure. (String) */

    @Override
    public HealthMeasureHistory updatePersonMeasure(int id, HealthMeasureHistory healthMeasureHistory) {
        Person existingPerson = Person.getPersonById(id);
        HealthMeasureHistory existingHistory = HealthMeasureHistory.getHealthMeasureHistoryById(healthMeasureHistory.getMid());
        List<HealthProfile> healthProfiles = existingPerson.getMeasureType();

        if (existingPerson == null) {
            System.out.println("Cannot find person with id: " + id);
        } else {
            String updatedMeasureType = healthMeasureHistory.getMeasureType();
            String updatedMeasureValue = healthMeasureHistory.getMeasureValue();
            String updatedMeasureValueType = healthMeasureHistory.getMeasureValueType();
            Date updatedDateRegistered = healthMeasureHistory.getDateRegistered();
            if (updatedMeasureType != null) {
                existingHistory.setMeasureType(updatedMeasureType);
            }
            if (updatedMeasureValue != null) {
                existingHistory.setMeasureValue(updatedMeasureValue);
            }
            if (updatedMeasureValueType != null) {
                existingHistory.setMeasureValueType(updatedMeasureValueType);
            }
            if (updatedDateRegistered != null) {
                existingHistory.setDateRegistered(updatedDateRegistered);
            }

            for (HealthProfile healthProfile: healthProfiles) {
                if (healthProfile.getMeasureType().equalsIgnoreCase(healthMeasureHistory.getMeasureType())) {
                    healthProfile.setMeasureValue(healthMeasureHistory.getMeasureValue());
                    healthProfile.setMeasureValueType(healthMeasureHistory.getMeasureValueType());
                    healthProfile.setDateRegistered(new Date());
                    HealthProfile.updateHealthProfile(healthProfile);
                }
            }
        }
        return HealthMeasureHistory.updateHealthMeasureHistory(existingHistory);
    }
}