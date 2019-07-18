package lt.bit.java2.spring.mvc.controllers;

import lt.bit.java2.spring.mvc.Employee;
import lt.bit.java2.spring.mvc.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
