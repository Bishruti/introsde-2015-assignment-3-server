package introsde.assignment.soap.model;

import introsde.assignment.soap.dao.LifeCoachDao;
import introsde.assignment.soap.model.Person;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

// The persistent class for the "HealthMeasureHistory" database table.
@Entity // Indicates that this class is an entity to persist in DB
@Table(name="HealthMeasureHistory")
//Queries to obtain HealthMeasureHistory by using various attributes.
@NamedQueries({
	@NamedQuery(name="HealthMeasureHistory.findAll", query="SELECT h FROM HealthMeasureHistory h"),
	@NamedQuery(name="HealthMeasureHistory.getTypes", query="SELECT DISTINCT h.measureType FROM HealthMeasureHistory h"),
	@NamedQuery(name="HealthMeasureHistory.getHistoryByPersonIdAndMeasureType", query="SELECT h FROM HealthMeasureHistory h WHERE h.person.idPerson = :idPerson AND h.measureType = :measureType"),
	@NamedQuery(name="HealthMeasureHistory.getHistoryByPersonIdAndMeasureId", query="SELECT h FROM HealthMeasureHistory h WHERE h.person.idPerson = :idPerson AND h.measureType= :measureType AND h.mid = :measureId"),
})

//@XmlRootElement(name = "measureHistory")
@XmlType(propOrder = {"mid", "dateRegistered", "measureType", "measureValue", "measureValueType" })
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable(false)
public class HealthMeasureHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id // Defines this attributed as the one that identifies the entity
	@GeneratedValue(generator="sqlite_healthmeasurehistory")
	@TableGenerator(name="sqlite_healthmeasurehistory")

	@Column(name="mid") // Id of HealthMeasureHistory
	private int mid;

	@Temporal(TemporalType.DATE)
	@Column(name="dateRegistered") // HealthMeasureHistory created date
	private Date dateRegistered;

	@Column(name="measureType") // Name of Measure
	private String measureType;

	@Column(name="measureValue") // Value of Measure
	private String measureValue;

	@Column(name="measureValueType") // Value Type of Measure
	private String measureValueType;

    // Creating relationship with Person
    @XmlTransient
	@ManyToOne
	@JoinColumn(name = "idPerson", referencedColumnName = "idPerson")
	private Person person;

	public HealthMeasureHistory() {
	}

    //Getters

	public int getMid() {
		return this.mid;
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

	public void setMid(int mid) {
		this.mid = mid;
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

	@XmlTransient
	public Person getPerson() {
	    return person;
	}

	public void setPerson(Person param) {
	    this.person = param;
	}

	// Database Operations
	public static HealthMeasureHistory getHealthMeasureHistoryById(int id) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		HealthMeasureHistory p = em.find(HealthMeasureHistory.class, id);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}

	public static HealthMeasureHistory getHealthMeasureHistoryByType(String id) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		HealthMeasureHistory p = em.find(HealthMeasureHistory.class, id);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}

	public static List<HealthMeasureHistory> getHealthHistoryOfPersonByMeasureType(int idPerson, String measureType) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		List<HealthMeasureHistory> list = new ArrayList<HealthMeasureHistory>();
		list = em.createNamedQuery("HealthMeasureHistory.getHistoryByPersonIdAndMeasureType", HealthMeasureHistory.class)
				.setParameter("idPerson", idPerson)
				.setParameter("measureType", measureType)
				.getResultList();
		LifeCoachDao.instance.closeConnections(em);
		return list;
	}

	public static List<HealthMeasureHistory> getMeasurebyMid(int idPerson, String measureType, int mid) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		List<HealthMeasureHistory> list = new ArrayList<HealthMeasureHistory>();
		list = em.createNamedQuery("HealthMeasureHistory.getHistoryByPersonIdAndMeasureId", HealthMeasureHistory.class)
				.setParameter("idPerson", idPerson)
				.setParameter("measureType", measureType)
				.setParameter("measureId", mid)
				.getResultList();
		LifeCoachDao.instance.closeConnections(em);
		return list;
	}

	public static List<String> getTypes() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		List<String> list = new ArrayList<String>();
		list = em.createNamedQuery("HealthMeasureHistory.getTypes", String.class)
				.getResultList();
		LifeCoachDao.instance.closeConnections(em);
		return list;
	}

	
	public static List<HealthMeasureHistory> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<HealthMeasureHistory> list = em.createNamedQuery("HealthMeasureHistory.findAll", HealthMeasureHistory.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static HealthMeasureHistory saveHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static HealthMeasureHistory updateHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removeHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}