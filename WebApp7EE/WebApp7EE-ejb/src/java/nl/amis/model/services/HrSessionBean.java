/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amis.model.services;

import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import nl.amis.model.entities.hr.Departments;
import nl.amis.model.entities.hr.Employees;

/**
 *
 * @author edwin
 */
@DataSourceDefinition(
        name            = "java:global/jdbc/hrDS2",
        minPoolSize     = 2,
        initialPoolSize = 2,
        className       = "oracle.jdbc.pool.OracleDataSource",
        url             = "jdbc:oracle:thin:@192.168.33.133:1521:test",
        user            = "hr",
        password        = "hr"
)


@Stateless
@LocalBean
public class HrSessionBean {

    @Resource
    Validator validator;

    @PersistenceContext(unitName = "WebApp7EE-ejbPU")
    private EntityManager em;

    public HrSessionBean() {
    }

    public Departments persistDepartment(Departments departments) {

        Set<ConstraintViolation<Departments>> violations = validator.validate(departments);
        System.out.println("error size: " + violations.size());
        for (ConstraintViolation<Departments> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            System.out.println("invalid value for: '" + propertyPath + "': " + message);
        }

        em.persist(departments);
        return departments;
    }

    @SuppressWarnings("unchecked")
    public List<Departments> getAllDepartments() {
        return em.createNamedQuery("Departments.findAll").getResultList();
    }

    public Departments mergeDepartment(Departments departments) {
        return em.merge(departments);
    }

    public void removeDepartment(Departments departments) {
        departments = em.find(Departments.class, departments.getDepartmentId());
        em.remove(departments);
    }

    public Employees mergeEmployee(Employees employees) {
        return em.merge(employees);
    }

    public void removeEmployee(Employees employees) {
        employees = em.find(Employees.class, employees.getEmployeeId());
        em.remove(employees);
    }
}
