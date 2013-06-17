/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amis.web.bean;

import java.io.Serializable;
import javax.faces.flow.FlowScoped;
import javax.inject.Named;

/**
 *
 * @author edwin
 */
@Named(value="flow1Bean")
@FlowScoped( value = "flow1")
public class FlowBean implements Serializable {

    public FlowBean() {
    }
    
    public String getName(){
        return this.getClass().getSimpleName();
    }
    
}
