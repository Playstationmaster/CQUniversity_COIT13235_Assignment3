package assignment3.sources;

import java.util.List;
import javax.persistence.*;

@Entity
@NamedQueries({
    @NamedQuery(name = "findAllProperties", query = "select p from Property p"),
})
public class Property {

    //Attributes
    @Id
    @GeneratedValue
    protected Long id;
    protected Integer streetNumber;
    protected String streetName;
    protected String city;
    protected String state;
    protected Integer postcode;
    protected String country;
    protected Integer numberOfBedrooms;
    protected Integer numberOfBathrooms;
    protected String description;
    protected String propertyType;
    
    @OneToMany(mappedBy = "property")
    private List<Allocation> allocations;

    //Constructors 
    public Property() {
    }

    public Property(Long id, Integer streetNumber, String streetName, String city, String state, Integer postcode, String country, Integer numberOfBedrooms, Integer numberOfBathrooms, String description, String propertyType) {
        this.id = id;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
        this.country = country;
        this.numberOfBedrooms = numberOfBedrooms;
        this.numberOfBathrooms = numberOfBathrooms;
        this.description = description;
        this.propertyType = propertyType;
    }
    

    //Getters & Setters         

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getPostcode() {
        return postcode;
    }

    public void setPostcode(Integer postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    public void setNumberOfBedrooms(Integer numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    public Integer getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public void setNumberOfBathrooms(Integer numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
   
    
}