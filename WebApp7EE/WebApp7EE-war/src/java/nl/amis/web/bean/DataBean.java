/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amis.web.bean;

import java.io.Serializable;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import nl.amis.jms.JmsSessionBean;

/**
 *
 * @author edwin
 */
@Named(value = "dataBean")
@ViewScoped
public class DataBean implements Serializable {

    private static final long serialVersionUID = -1L;
    
    @Inject
    JmsSessionBean jmsBean;
    
    public void sendMessage(ActionEvent event) {
        String output = jmsBean.sendMessage("Hello there");
        System.out.println("send jms: "+output);
        
    }

    @NotNull
    @Size(min = 2, max = 10)
    private String departmentName = "fieldCDI 1";

    @Size(min = 2, max = 50)
    @Pattern(regexp = "[A-Za-z ]*", message = "locationValidation")
    private String departmentLocation = "fieldCDI 2";

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    
    
    public String getDepartmentLocation() {
        return departmentLocation;
    }

    public void setDepartmentLocation(String departmentLocation) {
        this.departmentLocation = departmentLocation;
    }
}
