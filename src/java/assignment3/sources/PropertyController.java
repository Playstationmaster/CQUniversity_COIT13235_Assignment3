package assignment3.sources;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.Index;

@ManagedBean
@SessionScoped
public class PropertyController {

    //Attributes             
    @EJB
    private PropertyEJB propertyEJB;
    private Property property = new Property();
    private propertyForSale propertyForSale = new propertyForSale();
    private propertyForRent propertyForRent = new propertyForRent();
    private propertyForInRent propertyForInRent = new propertyForInRent();
    private Manager manager = new Manager();
    private Allocation allocation = new Allocation();
    private List<propertyForSale> salePropertyList = new ArrayList<propertyForSale>();
    private List<propertyForRent> rentPropertyList = new ArrayList<propertyForRent>();
    private List<propertyForInRent> inRentPropertyList = new ArrayList<propertyForInRent>();
    private List<Manager> managerList = new ArrayList<Manager>();
    private List<Allocation> allocationList = new ArrayList<Allocation>();
    private Long requestedIndex; // For search, delete or update ID
    private String resultReturn; // This String is for debugging purpose
    private String errorDescription; // Describe what caused errors. Be user friendly
    private String searchFN;
    private String searchLN;
    private Manager firstName;
    private Manager lastName;
    
    private Long requestedIndexSub; // Use these variables when handling two different IDs
    private Long handleIndex1;
    private Long handleIndex2;
    private Long handleIndex3;
    
    //private List<Allocation> mgrAllocationList = new ArrayList<Allocation>();

    //Public methods    
    public String doToIndex() {
        return "./index.xhtml";
    }
    
    public String doNewSale() {
        this.propertyForSale = new propertyForSale();
        return "createSaleProperty.faces";
    }
    
    public String doNewRent() {
        this.propertyForRent = new propertyForRent();
        return "createRentalProperty.faces";
    }
    
    public String doNewInRent() {
        this.propertyForInRent = new propertyForInRent();
        return "createInRentProperty.faces";
    }
    
    public String doNewManager() {
        this.manager = new Manager();
        return "createManager.faces";
    }
    
    public String doNewAllocation() {
        this.allocation = new Allocation();
        return "createAllocation.faces";
    }
    
    // Changing a rent prop to in-rent prop. Code might be inefficient for exception handling
    public void propertyInRentTransferRequest() {
        propertyForRent rentProp = propertyEJB.findRentPropertyById(requestedIndex);
        try {
            propertyForInRent.setCity(rentProp.getCity());
            propertyForInRent.setCountry(rentProp.getCountry());
            propertyForInRent.setDescription(rentProp.getDescription());
            propertyForInRent.setNumberOfBathrooms(rentProp.getNumberOfBathrooms());
            propertyForInRent.setNumberOfBedrooms(rentProp.getNumberOfBedrooms());
            propertyForInRent.setPostcode(rentProp.getPostcode());
            propertyForInRent.setPropertyType(rentProp.getPropertyType());
            propertyForInRent.setRentalPrice(rentProp.getRentalPrice());
            propertyForInRent.setState(rentProp.getState());
            propertyForInRent.setStreetName(rentProp.getStreetName());
            propertyForInRent.setStreetNumber(rentProp.getStreetNumber());
            propertyForInRent.setId(rentProp.getId());
                
            propertyEJB.deleteRentProperty(rentProp);
            propertyEJB.createPropertyForInRent(propertyForInRent);
            resultReturn = "Property successfully created: " + propertyForInRent.getStreetNumber() + " " + propertyForInRent.getStreetName() + ", " + propertyForInRent.getCity() + " " + propertyForInRent.getPostcode();
        } catch(Exception e) {
            resultReturn = "Requested property cannot be found: " + requestedIndex;
        }
    }
    
    public void doFindSaleById(Long requestedIndex) {
        propertyForSale = propertyEJB.findSaleById(requestedIndex);
    }
    
    public void propRefreshSale() {
        propertyForSale = propertyEJB.findSaleById(propertyForSale.getId());
    }
      
    public void propRefreshRent() {
        propertyForRent = propertyEJB.findRentPropertyById(propertyForRent.getId());
    }
    
    public void propRefreshInRent() {
        propertyForInRent = propertyEJB.findInRentPropertyById(propertyForInRent.getId());
    }
    
    public void doFindManagerById(Long requestedIndex){
        manager = propertyEJB.findManagerById(requestedIndex);
    }
    
    public void propRefreshManager() {
        manager = propertyEJB.findManagerById(manager.getId());
        // mgrAllocationListReload(); //unused due to Java heap space issue
    }
    
    public void mgrAllocationListReload() {
        allocationList = propertyEJB.findAllocations();
        List mgrAllocationList = new ArrayList();
        for(int i=0;i<allocationList.size();) {
            if(Objects.equals(allocationList.get(i).displayMgrFN(), manager.getFirstName()) && Objects.equals(allocationList.get(i).displayMgrLN(), manager.getLastName())) {
                mgrAllocationList.add(allocationList.get(i));
            }
        }
        allocationList = mgrAllocationList;
    }
    
    public void doFindRentById(Long requestedIndex) {
        propertyForRent = propertyEJB.findRentPropertyById(requestedIndex);
    }
    
    public void doFindInRentById(Long requestedIndex) {
        propertyForInRent = propertyEJB.findInRentPropertyById(requestedIndex);
    }
    
    public void allocationRefresh() {
        allocation = propertyEJB.findAllocationById(allocation.getId());
    }
    
    public void allocationRemoval() {
        try{
            propertyEJB.deleteAllocation(allocation);
            resultReturn = "Allocation deleted successfully. ";
        }
        catch(Exception e) {
            resultReturn = "Failed to delete allocation. ";
        }
    }
    
    public String doSearchInRent() {
        try{
            doFindInRentById(requestedIndex);
            resultReturn = "Property found! " + propertyForInRent.getId(); // Simple verification. If crash, item invalid
            resultReturn = doViewInRentPropertyDetails();
        }catch(Exception e) {
            resultReturn = doHandleNotFoundPage();
            errorDescription = "The property allocated with given ID was not discovered. \nAre you sure that you typed in the ID for the exact type of property? ";
        }
        return resultReturn;
    }
    
    public String doSearchRent() {
        try{
            doFindRentById(requestedIndex);
            resultReturn = "Property found! " + propertyForRent.getId(); // Simple verification. If crash, item invalid
            resultReturn = doViewRentalPropertyDetails();
        }catch(Exception e) {
            resultReturn = doHandleNotFoundPage();
            errorDescription = "The property allocated with given ID was not discovered. \nAre you sure that you typed in the ID for the exact type of property? ";
        }
        return resultReturn;
    }
    
    public String doSearchSale() {
        try{
            doFindSaleById(requestedIndex);
            resultReturn = "Property found! " + propertyForInRent.getId(); // Simple verification. If crash, item invalid
            resultReturn = doViewSaleDetails();
        }catch(Exception e) {
            resultReturn = doHandleNotFoundPage();
            errorDescription = "The property allocated with given ID was not discovered. \nAre you sure that you typed in the ID for the exact type of property? ";
        }
        return resultReturn;
    }
    
    public String doSerachManager() {
        try{
            manager = propertyEJB.findMangerByName(searchFN, searchLN);
            resultReturn = "Property found! " + manager.getFirstName();
            if(!manager.getFirstName().equals("")) {
                resultReturn = doViewManagerDetails();
            } else {
                resultReturn = doHandleNotFoundPage();
                errorDescription = "The manager was not found. Please type both first name and last name with no typos. ";
            }
        } 
        catch(Exception e) {
            resultReturn = doHandleNotFoundPage();
            errorDescription = "The manager was not found. Please type both first name and last name with no typos. ";
        }
        return resultReturn;
    }
    
    public String doSearchAllocation() {
        try{
            doFindAllocationsById(requestedIndex);
            resultReturn = "Property found! " + allocation.getId(); // Simple verification. If crash, item invalid
            resultReturn = "allocationReturn.faces";
        }catch(Exception e) {
            resultReturn = doHandleNotFoundPage();
            errorDescription = "The property allocated with given ID was not discovered. \nAre you sure that you typed in the ID for the exact type of property? ";
        }
        return resultReturn;
    }
    
    public void resetObjects() {
        propertyForInRent = new propertyForInRent();
        propertyForRent = new propertyForRent();
        propertyForSale = new propertyForSale();
        manager = new Manager();
        allocation = new Allocation();
    }
    
    public String doViewRentalPropertyDetails() {
        return "rentalDetailPage.faces";
    }
    
    public String doViewInRentPropertyDetails() {
        return "inRentDetailPage.faces";
    }
    
    public String doViewManagerDetails() {
        return "managerDetailPage.faces";
    }
    
    public String doViewSaleDetails() {
        return "saleDetailPage.faces";
    }
    
    public String doHandleNotFoundPage() {
        return "propNotFound.xhtml";
    }
    
    public String doToSalePropertyList() {
        salePropertyList = propertyEJB.findSaleProperties();
        return "listSaleProperties.faces";
    }
    
    public String doToRentalPropertyList() {
        rentPropertyList = propertyEJB.findRentalProperties();
        return "listRentalProperties.faces";
    }
    
    public String doToInRentPropertyList() {
        inRentPropertyList = propertyEJB.findInRentProperties();
        return "listInRentProperties.faces";
    }
    
    public String doToManagerList() {
        managerList = propertyEJB.findManagers();
        return "listManagers.faces";
    }
    
    public String doToAllocationList() {
        allocationList = propertyEJB.findAllocations();
        return "listAllocations.faces";
    }
    
    public String doCreateSaleProperty() {
        propertyForSale = propertyEJB.createPropertyForSale(propertyForSale);
        salePropertyList = propertyEJB.findSaleProperties();
        return "landingPageForSaleProperty.faces";
    }
    
    public String doCreateRentalProperty() {
        propertyForRent = propertyEJB.createPropertyForRent(propertyForRent);
        rentPropertyList = propertyEJB.findRentalProperties();
        return "landingPageForRentalProperty.faces";
    }
    
    public String doCreateInRentProperty() {
        propertyInRentTransferRequest(); // This function handles the data migration
        inRentPropertyList = propertyEJB.findInRentProperties();
        return "landingPageForInRentProperty.faces";
    }
    
    public String doCreateManager() {
        manager = propertyEJB.createManager(manager);
        managerList = propertyEJB.findManagers();
        return "listManagers.faces";
    }
    
    public String prepareAllocationSale() {
        doFindManagerById(requestedIndexSub);
        allocation.setManager(manager);
        property = new Property();
        doFindSaleById(handleIndex1);
        property.setId(propertyForSale.getId());
        property.setDescription("Sale property");
        property.setCity(propertyForSale.getCity());
        property.setCountry(propertyForSale.getCountry());
        property.setNumberOfBathrooms(propertyForSale.getNumberOfBathrooms());
        property.setNumberOfBedrooms(propertyForSale.getNumberOfBedrooms());
        property.setPostcode(propertyForSale.getPostcode());
        property.setPropertyType(propertyForSale.getPropertyType());
        property.setState(propertyForSale.getState());
        property.setStreetName(propertyForSale.getStreetName());
        property.setStreetNumber(propertyForSale.getStreetNumber());
        allocation.setProperty(property);
        allocation.setCreationDate(new Date().toString());
        allocation = propertyEJB.createAllocation(allocation);
        allocationList = propertyEJB.findAllocations();
        return "listAllocations.faces";
    }
    
    public String prepareAllocationRent() {
        doFindManagerById(requestedIndexSub);
        allocation.setManager(manager);
        property = new Property();
        doFindRentById(handleIndex2);
        property.setId(propertyForRent.getId());
        property.setDescription("Rental property");
        property.setCity(propertyForRent.getCity());
        property.setCountry(propertyForRent.getCountry());
        property.setNumberOfBathrooms(propertyForRent.getNumberOfBathrooms());
        property.setNumberOfBedrooms(propertyForRent.getNumberOfBedrooms());
        property.setPostcode(propertyForRent.getPostcode());
        property.setPropertyType(propertyForRent.getPropertyType());
        property.setState(propertyForRent.getState());
        property.setStreetName(propertyForRent.getStreetName());
        property.setStreetNumber(propertyForRent.getStreetNumber());
        allocation.setProperty(property);
        allocation.setCreationDate(new Date().toString());
        allocation = propertyEJB.createAllocation(allocation);
        allocationList = propertyEJB.findAllocations();
        return "listAllocations.faces";
    }
    
    public String prepareAllocationInRent() {
        doFindManagerById(requestedIndexSub);
        allocation.setManager(manager);
        property = new Property();
        doFindInRentById(handleIndex3);
        property.setId(propertyForInRent.getId());
        property.setDescription("InRent property");
        property.setCity(propertyForInRent.getCity());
        property.setCountry(propertyForInRent.getCountry());
        property.setNumberOfBathrooms(propertyForInRent.getNumberOfBathrooms());
        property.setNumberOfBedrooms(propertyForInRent.getNumberOfBedrooms());
        property.setPostcode(propertyForInRent.getPostcode());
        property.setPropertyType(propertyForInRent.getPropertyType());
        property.setState(propertyForInRent.getState());
        property.setStreetName(propertyForInRent.getStreetName());
        property.setStreetNumber(propertyForInRent.getStreetNumber());
        allocation.setProperty(property);
        allocation.setCreationDate(new Date().toString());
        allocation = propertyEJB.createAllocation(allocation);
        allocationList = propertyEJB.findAllocations();
        return "listAllocations.faces";
    }
    
    public void doFindAllocationsById(Long index) {
        allocation = propertyEJB.findAllocationById(index);
    }
    
    //Getters & Setters         
    public String getResultReturn() {
        return resultReturn;
    }

    public void setResultReturn(String resultReturn) {
        this.resultReturn = resultReturn;
    }
    
    public PropertyEJB getPropertyEJB() {
        return propertyEJB;
    }

    public void setPropertyEJB(PropertyEJB propertyEJB) {
        this.propertyEJB = propertyEJB;
    }
    
    public Long getRequestedIndexSub() {
        return requestedIndexSub;
    }

    public void setRequestedIndexSub(Long requestedIndexSub) {
        this.requestedIndexSub = requestedIndexSub;
    }

    public propertyForSale getPropertyForSale() {
        return propertyForSale;
    }

    public void setPropertyForSale(propertyForSale propertyForSale) {
        this.propertyForSale = propertyForSale;
    }

    public propertyForRent getPropertyForRent() {
        return propertyForRent;
    }

    public void setPropertyForRent(propertyForRent propertyForRent) {
        this.propertyForRent = propertyForRent;
    }
    
    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public propertyForInRent getPropertyForInRent() {
        return propertyForInRent;
    }

    public void setPropertyForInRent(propertyForInRent propertyForInRent) {
        this.propertyForInRent = propertyForInRent;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public List<Manager> getManagerList() {
        this.managerList = propertyEJB.findManagers();
        return managerList;
    }

    public void setManagerList(List<Manager> managerList) {
        this.managerList = managerList;
    }
    
    public String getSearchFN() {
        return searchFN;
    }

    public void setSearchFN(String searchFN) {
        this.searchFN = searchFN;
    }

    public String getSearchLN() {
        return searchLN;
    }

    public void setSearchLN(String searchLN) {
        this.searchLN = searchLN;
    }

    public List<propertyForSale> getSalePropertyList() {
        salePropertyList = propertyEJB.findSaleProperties();
        return salePropertyList;
    }

    public void setSalePropertyList(List<propertyForSale> salePropertyList) {
        this.salePropertyList = salePropertyList;
    }

    public List<propertyForRent> getRentPropertyList() {
        rentPropertyList = propertyEJB.findRentalProperties();
        return rentPropertyList;
    }
    
    public int getManagerCount() {
        return managerList.size();
    }
    
    public List<Allocation> getAllocationList() {
        allocationList = propertyEJB.findAllocations();
        return allocationList;
    }
    
    public Allocation getAllocation() {
        return allocation;
    }
    
    public void setAllocation(Allocation allocation) {
        this.allocation = allocation;
    }

    public void setAllocationList(List<Allocation> allocationList) {
        this.allocationList = allocationList;
    }

    public int getAllocationCount() {
        return allocationList.size();
    }

    public void setRentPropertyList(List<propertyForRent> rentPropertyList) {
        this.rentPropertyList = rentPropertyList;
    }

    public List<propertyForInRent> getInRentPropertyList() {
        inRentPropertyList = propertyEJB.findInRentProperties();
        return inRentPropertyList;
    }

    public void setInRentPropertyList(List<propertyForInRent> inRentPropertyList) {
        this.inRentPropertyList = inRentPropertyList;
    }

    public int getSalePropertyCount() {
        return salePropertyList.size();
    }

    public int getRentPropertyCount() {
        return rentPropertyList.size();
    }

    public int getInRentPropertyCount() {
        return inRentPropertyList.size();
    }
    
    public Long getRequestedIndex() {
        return requestedIndex;
    }

    public void setRequestedIndex(Long requestedIndex) {
        this.requestedIndex = requestedIndex;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Manager getFirstName() {
        return firstName;
    }

    public void setFirstName(Manager firstName) {
        this.firstName = firstName;
    }

    public Manager getLastName() {
        return lastName;
    }

    public void setLastName(Manager lastName) {
        this.lastName = lastName;
    }
    // ===============
    
    public Long getHandleIndex1() {
        return handleIndex1;
    }

    public void setHandleIndex1(Long handleIndex1) {
        this.handleIndex1 = handleIndex1;
    }

    public Long getHandleIndex2() {
        return handleIndex2;
    }

    public void setHandleIndex2(Long handleIndex2) {
        this.handleIndex2 = handleIndex2;
    }

    public Long getHandleIndex3() {
        return handleIndex3;
    }

    public void setHandleIndex3(Long handleIndex3) {
        this.handleIndex3 = handleIndex3;
    }
    /*
    public List<Allocation> getMgrAllocationList() {
        return mgrAllocationList;
    }

    public void setMgrAllocationList(List<Allocation> mgrAllocationList) {
        this.mgrAllocationList = mgrAllocationList;
    }
    */
    
}