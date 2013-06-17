/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amis.web.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import nl.amis.model.entities.hr.Departments;
import nl.amis.model.services.HrSessionBean;

/**
 *
 * @author edwin
 */
@Named(value = "hrBean")
@ViewScoped
public class HrBean implements Serializable {

    public HrBean() {
    }
    private static final long serialVersionUID = -1L;

    @Inject
    private HrSessionBean sessionFacade;


    public List<Departments> getAllDepartments() {
        List<Departments> result = sessionFacade.getAllDepartments();
        return result;
    }
    private Validator validator;

    public void addDepartment(ActionEvent event) {

        if (validator == null) {
            validator = Validation.buildDefaultValidatorFactory().getValidator();
        }
        Departments dept = new Departments();
        dept.setDepartmentName("1234567890123456789012345678901234567890");

        Set<ConstraintViolation<Departments>> violations = validator.validate(dept);
        for (ConstraintViolation<Departments> violation : violations) {
            System.out.println("error size: " + violations.size());

            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            FacesMessage message2 =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Invalid value for: '" + propertyPath + "': " + message,
                    "Validation Error ");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(event.getComponent().getClientId(), message2);
        }
        if (violations.size() > 0) {
            return;
        }
        sessionFacade.persistDepartment(dept);
    }
}
