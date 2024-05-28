package assignment3.sources;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "rp.findAllProperties", query = "select p from propertyForRent p"),
})
public class propertyForRent extends Property {

    //Attributes             
    private Float rentalPrice;
    
    //Constructors            
    public propertyForRent() {
    }

    public propertyForRent(Float rentalPrice, Long id, String address, Integer streetNumber, String streetName, String city, String state, Integer postcode, String country, Integer numberOfBedrooms, Integer numberOfBathrooms, String description, String propertyType) {
        super(id, streetNumber, streetName, city, state, postcode, country, numberOfBedrooms, numberOfBathrooms, description, propertyType);
        this.rentalPrice = rentalPrice;
    }

    //Getters & Setters
    public Float getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(Float rentalPrice) {
        this.rentalPrice = rentalPrice;
    } 

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