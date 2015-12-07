package introsde.assignment.soap.model;

import introsde.assignment.soap.dao.LifeCoachDao;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity  // Indicates that this class is an entity to persist in DB
@Table(name="Person")
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
//@XmlRootElement
@XmlType(propOrder = { "idPerson", "firstname", "lastname", "birthdate", "measureType"})
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable(false)
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id // Defines this attributed as the one that identifies the entity
    @GeneratedValue(generator="sqlite_person")
    @TableGenerator(name="sqlite_person")

    @Column(name="idPerson") // Id of the Person
    private int idPerson;
    @Column(name="firstname") // Firstname of the Person
    private String firstname;
    @Column(name="lastname") // Lastname of the Person
    private String lastname;
    @Temporal(TemporalType.DATE) // Defines the precision of the date attribute
    @Column(name="birthdate")
    private Date birthdate;

    // Creating relationship with HealthProfile
    @XmlElementWrapper(name = "currentHealth")
    @OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<HealthProfile> measureType;

    public List<HealthProfile> getMeasureType () {
        return measureType;
    }

    public void setMeasureType(List<HealthProfile> measureType) {
        this.measureType = measureType;
    }

    //Creating relationship with HealthMeasureHistory
    @XmlTransient
    @OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<HealthMeasureHistory> measure;
    public List<HealthMeasureHistory> getMeasure() {
        return measure;
    }
    
    // Getters
    @XmlTransient
    public int getIdPerson(){
        return idPerson;
    }
    public String getLastname(){
        return lastname;
    }
    public String getFirstname(){
        return firstname;
    }
    public String getBirthdate(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        // Get the date today using Calendar object.
        return df.format(birthdate);
    }
    
    // Setters
    public void setIdPerson(int idPerson){
        this.idPerson = idPerson;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }
    public void setBirthdate(String birthdate) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date date = format.parse(birthdate);
        this.birthdate = date;
    }


    // Database Operations
    public static Person getPersonById(int personId) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        Person p = em.find(Person.class, personId);
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static List<Person> getAll() {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        List<Person> list = em.createNamedQuery("Person.findAll", Person.class)
            .getResultList();
        LifeCoachDao.instance.closeConnections(em);
        return list;
    }

    public static Person savePerson(Person p) {
        appendHealthProfile(p);
        appendHealthMeasureHistory(p);
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static Person updatePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static void removePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
    }

    private static Person appendHealthProfile(Person p) {
        java.util.Date date = new java.util.Date();
        for (HealthProfile healthProfile : p.measureType) {
            healthProfile.setPerson(p);
            healthProfile.setDateRegistered(date);
        }
        return p;
    }

    private static Person appendHealthMeasureHistory(Person p) {
        java.util.Date date = new java.util.Date();
        p.measure = new ArrayList<HealthMeasureHistory>(p.measureType.size());

        for (HealthProfile healthProfile : p.measureType) {
            HealthMeasureHistory healthMeasureHistory = new HealthMeasureHistory();
            healthMeasureHistory.setPerson(p);
            healthMeasureHistory.setDateRegistered(date);
            healthMeasureHistory.setMeasureType(healthProfile.getMeasureType());
            healthMeasureHistory.setMeasureValue(healthProfile.getMeasureValue());
            healthMeasureHistory.setMeasureValueType(healthProfile.getMeasureValueType());
            p.measure.add(healthMeasureHistory);
        }
        return p;
    }
}