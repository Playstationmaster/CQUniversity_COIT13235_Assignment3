package assignment3.sources;



import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "irp.findAllProperties", query = "select p from propertyForInRent p"),
})
public class propertyForInRent extends Property {
    //Attributes             
    private Float rentalPrice;
    private String tenant;
    private Integer rentalPeriod;

    //Constructors            
    public propertyForInRent() {
    }

    public propertyForInRent(Float rentalPrice, String tenant, Integer rentalPeriod, Long id, String address, Integer streetNumber, String streetName, String city, String state, Integer postcode, String country, Integer numberOfBedrooms, Integer numberOfBathrooms, String description, String propertyType) {
        super(id, streetNumber, streetName, city, state, postcode, country, numberOfBedrooms, numberOfBathrooms, description, propertyType);
        this.rentalPrice = rentalPrice;
        this.tenant = tenant;
        this.rentalPeriod = rentalPeriod;
    }
    
    
    //Getters & Setters

    public Float getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(Float rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public Integer getRentalPeriod() {
        return rentalPeriod;
    }

    public void setRentalPeriod(Integer rentalPeriod) {
        this.rentalPeriod = rentalPeriod;
    }
    
}