package assignment3.sources;

import javax.persistence.*;
import assignment3.sources.Property;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
    @NamedQuery(name = "findAllManagers", query = "select m from Manager m"),
})
public class Manager {
    //Attributes 
    @Id
    @GeneratedValue
    @Column(name = "manager_id")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String mobile;
    
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "manager_fk")
    private static List<Allocation> allocation = new ArrayList<Allocation>();

    //Constructors 
    public Manager() {
    }

    public Manager(String firstName, String lastName, String email, String phone, String mobile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.mobile = mobile;
    }

    //Getters & Setters         
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }    

    public List<Allocation> getAllocation() {
        return allocation;
    }

    public static void setAllocation(List<Allocation> allocation) {
        Manager.allocation = allocation;
    }
}