package lt.bit.java2.spring.mvc.services;

import lt.bit.java2.spring.mvc.Employee;
import lt.bit.java2.spring.mvc.services.EmployeeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    EmployeeRowMapper employeeRowMapper;

    public Employee getEmployeeById(int id) {
//        Employee employee = new Employee();
//        employee.setEmpNo(id);
//        employee.setFirstName("Jonas");
//        employee.setLastName("Jonaitis");
//        employee.setBirthDate(LocalDate.of(1986, 12, 20));
//        employee.setHireDate(LocalDate.of(1999, 7, 1));
//        employee.setGender(Gender.M);
//        return employee;

       Employee employee = jdbcTemplate.queryForObject(
               "SELECT * FROM employees WHERE emp_no = ?",
               employeeRowMapper,
               id);
       return employee;

    }
}
