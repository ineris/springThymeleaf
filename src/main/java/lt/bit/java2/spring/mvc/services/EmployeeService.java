package lt.bit.java2.spring.mvc.services;

import lt.bit.java2.spring.mvc.Employee;
import lt.bit.java2.spring.mvc.Gender;
import lt.bit.java2.spring.mvc.services.EmployeeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.awt.print.Book;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    EmployeeRowMapper employeeRowMapper;

    public Employee getEmployeeById(int id) {

        Employee employee = jdbcTemplate.queryForObject(
                "SELECT * FROM employees WHERE emp_no = ?",
                employeeRowMapper,
                id);
        return employee;
    }

    public List<Employee> findAll() {
        List<Employee> employeeslist = jdbcTemplate.query("SELECT * FROM employees LIMIT 502", employeeRowMapper);
        return employeeslist;
    }


    public Page<Employee> findPaginated(Pageable pageable) {
        List<Employee> employees = findAll();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Employee> list;

        if (employees.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, employees.size());
            list = employees.subList(startItem, toIndex);
        }

        Page<Employee> employeePage
                = new PageImpl<Employee>(list, PageRequest.of(currentPage, pageSize), employees.size());

        return employeePage;
    }

}


