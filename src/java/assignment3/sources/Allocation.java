package assignment3.sources;

import assignment3.sources.Manager;
import assignment3.sources.Property;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@NamedQueries({
    @NamedQuery(name = "alc.findAllAllocations", query = "select alc from Allocation alc")
})

@Table(name = "Allocation")
public class Allocation implements Serializable {

    // attributes
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "manager_fk", referencedColumnName = "manager_id")
    private Manager manager;
    private Property property;
    private String creationDate;

    // constructors
    public Allocation() {
        
    }
    
    public Allocation(Long id, Manager manager, Property property, String date) {
        this.id = id;
        this.manager = manager;
        this.property = property;
        this.creationDate = date;
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
    
    public String getCreationDate() {
        return creationDate;
    }
    
    public void setCreationDate(String date) {
        this.creationDate = date;
    }
    
    public String displayMgrFN() {
        return manager.getFirstName();
    }
    
    public String displayMgrLN() {
        return manager.getLastName();
    }
}