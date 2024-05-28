package assignment3.sources;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class PropertyEJB {

    //Attributes             
    @PersistenceContext(unitName = "ASSIGNMENT")
    private EntityManager em;

    //Public methods           
    public List<Property> findProperties() {
        Query query = em.createNamedQuery("findAllProperties");
        return query.getResultList();
    }
    
    public List<propertyForSale> findSaleProperties() {
        Query query = em.createNamedQuery("sp.findAllProperties");
        return query.getResultList();
    }
    
    public List<propertyForRent> findRentalProperties() {
        Query query = em.createNamedQuery("rp.findAllProperties");
        return query.getResultList();
    }
    
    public List<propertyForInRent> findInRentProperties() {
        Query query = em.createNamedQuery("irp.findAllProperties");
        return query.getResultList();
    }
    
    public List<Manager> findManagers(){
        Query query = em.createNamedQuery("findAllManagers");
        return query.getResultList();
    }
    
    public List<Allocation> findAllocations() {
        Query query = em.createNamedQuery("alc.findAllAllocations");
        return query.getResultList();
    }
    
    public propertyForSale createPropertyForSale(propertyForSale propertyForSale) {
        em.persist(propertyForSale);
        return propertyForSale;
    }
    
    public propertyForRent createPropertyForRent(propertyForRent propertyForRent) {
        em.persist(propertyForRent);
        return propertyForRent;
    }
    
    public propertyForInRent createPropertyForInRent(propertyForInRent propertyForInRent) {
        em.persist(propertyForInRent);
        return propertyForInRent;
    }
    
    public Manager createManager(Manager manager){
        em.persist(manager);
        return manager;
    }
    
    public Allocation createAllocation(Allocation allocation){
        em.persist(allocation);
        return allocation;
    }
    
    public void deleteRentProperty(propertyForRent prop) {
        em.remove(em.merge(prop));
    }
    
    public propertyForRent findRentPropertyById(Long id) {
        return em.find(propertyForRent.class, id);
    }
    
    public propertyForInRent findInRentPropertyById(Long id) {
        return em.find(propertyForInRent.class, id);
    }
    
    public propertyForSale findSaleById(Long id) {
       return em.find(propertyForSale.class, id);
    }
    
    public Manager findManagerById(Long id){
        return em.find(Manager.class, id);
    }
    
    public Allocation findAllocationById(Long id){
        return em.find(Allocation.class, id);
    }
    
    public Manager findMangerByName(String firstName, String lastName){
        Manager tempManager = new Manager();
        List<Manager> tempML = findManagers();
        for(int i=0;i<tempML.size();) {
            if(firstName.equals(tempML.get(i).getFirstName()) && lastName.equals(tempML.get(i).getLastName())) {
                tempManager = tempML.get(i);
                break;
            }
            i++;
        }
        return tempManager;
    }
    
    public void deleteAllocation(Allocation all) {
        em.remove(em.merge(all));
    }
}