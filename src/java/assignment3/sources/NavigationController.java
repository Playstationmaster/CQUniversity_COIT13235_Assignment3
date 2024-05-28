package assignment3.sources;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author Kenneth
 */
@ManagedBean(name = "navigationController", eager = true)
@RequestScoped
public class NavigationController {

    @ManagedProperty(value = "#{param.PageId}")
    private String pageId;
    
    public String moveToPage1() {      
        return "createSaleProperty";    
    }  

    public String moveToPage2() {       
        return "listSaleProperties";    
    }  
    
    public String moveToHomePage() {      
        return "index";    
    }

    public String processPage1() {       
        return "page";    
    }  
   
    public String processPage2() {       
        return "page";    
    } 
   
    public String showPage() {       
        if(pageId == null) {          
            return "index";       
        }       
      
        if(pageId.equals("1")) {          
            return "createSaleProperty";       
        }else if(pageId.equals("2")) {          
            return "listSaleProperties";       
        }else {          
            return "index";       
        }    
    }
   
   public String getPageId() {       
      return pageId;    
   }  
   
   public void setPageId(String pageId) {       
      this.pageId = pageId;   
   } 
    
}
