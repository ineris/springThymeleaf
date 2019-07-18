package lt.bit.java2.spring.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@SpringBootApplication
public class MvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvcApplication.class, args);
    }

}

@Controller
@RequestMapping("/employee")
class EmployeeControler {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/{id}")
    public String getEmployee(@PathVariable int id, ModelMap map) {
        Employee employee = employeeService.getEmployeeById(id);
        map.addAttribute("employee", employee);
        return "employee";
    }

    @GetMapping
    String getEmployeesList() {
        return "employees-list";
    }
}

@Service
class EmployeeService {

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

@Service
class EmployeeRowMapper implements RowMapper<Employee>{

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee e = new Employee();
        e.setEmpNo(rs.getInt("emp_no"));
        e.setFirstName(rs.getString("first_name"));
        e.setLastName(rs.getString("last_name"));
        e.setBirthDate(rs.getDate("birth_date").toLocalDate());
        e.setHireDate(rs.getDate("hire_date").toLocalDate());
        e.setGender(Gender.valueOf(rs.getString("gender")));

        return e;
    }
}

