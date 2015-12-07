package introsde.assignment.soap.model;

import introsde.assignment.soap.dao.LifeCoachDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

// The persistent class for the "HealthProfile" database table.

@Entity // Indicates that this class is an entity to persist in DB
@Table(name = "HealthProfile")
@NamedQueries({
        @NamedQuery(name = "HealthProfile.findAll", query = "SELECT l FROM HealthProfile l"),
        @NamedQuery(name = "HealthProfile.getHealthProfileByPerson", query = "SELECT h FROM HealthProfile h WHERE h.person.idPerson = :idperson")
})

//@XmlRootElement(name = "measure")
@XmlType(propOrder = {"idHealthProfile", "dateRegistered", "measureType", "measureValue", "measureValueType" })
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable(false)
public class HealthProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id // Defines this attributed as the one that identifies the entity
    @GeneratedValue(generator="sqlite_healthprofile")
    @TableGenerator(name="sqlite_healthprofile")

    @Column(name = "idHealthProfile") // Id of HealthProfile
    private int idHealthProfile;

    @Temporal(TemporalType.DATE)
    @Column(name="dateRegistered") // Health Profile Created Date
    private Date dateRegistered;

    @Column(name = "measureType") // Measure of HealthProfile
    private String measureType;

    @Column(name = "measureValue") // Value of HealthProfile
    private String measureValue;

    @Column(name = "measureValueType") // Value Type of HealthProfile
    private String measureValueType;

    // Creating relationship with Person
    @XmlTransient
    @ManyToOne
    @JoinColumn(name="idPerson",referencedColumnName="idPerson")
    private Person person;

    public HealthProfile() {
    }

    //Getters
    @XmlTransient
    public int getIdHealthProfile() {
        return this.idHealthProfile;
    }

    public Date getDateRegistered() {
        return this.dateRegistered;
    }

    public String getMeasureType() {
        return this.measureType;
    }

    public String getMeasureValue() {
        return this.measureValue;
    }

    public String getMeasureValueType() {
        return this.measureValueType;
    }

    //Setters
    public void setIdHealthProfile(int idHealthProfile) {
        this.idHealthProfile = idHealthProfile;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType;
    }

    public void setMeasureValue(String measureValue) {
        this.measureValue = measureValue;
    }

    public void setMeasureValueType(String measureValueType) {
        this.measureValueType = measureValueType;
    }

    // we make this transient for JAXB to avoid and infinite loop on serialization
    @XmlTransient
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    // Database operations

    public static HealthProfile getHealthProfileByPersonId(int idPerson) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        HealthProfile p = em.find(HealthProfile.class, idPerson);
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static List<HealthProfile> getHealthProfileByPerson(int idPerson) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        List<HealthProfile> list = new ArrayList<HealthProfile>();
        list = em.createNamedQuery("HealthProfile.getHealthProfileByPerson", HealthProfile.class)
                .setParameter("idperson", idPerson)
                .getResultList();
        LifeCoachDao.instance.closeConnections(em);
        return list;
    }

    public static HealthProfile getHealthProfileById(int idHealthProfile) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        HealthProfile p = em.find(HealthProfile.class, idHealthProfile);
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static HealthProfile getHealthProfileByMeasure(String measure) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        HealthProfile p = em.find(HealthProfile.class, measure);
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static List<HealthProfile> getAll() {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        List<HealthProfile> list = em.createNamedQuery("HealthProfile.findAll", HealthProfile.class).getResultList();
        LifeCoachDao.instance.closeConnections(em);
        return list;
    }

    public static HealthProfile saveHealthProfile(HealthProfile p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static HealthProfile updateHealthProfile(HealthProfile p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static void removeHealthProfile(HealthProfile p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
    }
}
