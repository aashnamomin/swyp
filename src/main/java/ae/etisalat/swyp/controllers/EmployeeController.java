package ae.etisalat.swyp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ae.etisalat.swyp.dto.EmployeeDto;
import ae.etisalat.swyp.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = "add/employee", method = RequestMethod.POST)
	public EmployeeDto  addEmployee(@RequestBody EmployeeDto dto) {
		return employeeService.createEmployee(dto);
	}

	
	  @RequestMapping(value = "get/employee", method = RequestMethod.GET) public
	  EmployeeDto getEmployee(@RequestParam("empId") String empId) { return
	  employeeService.getEmployeeData(empId);
	  
	  }
	 
	  @RequestMapping(value = "update/employee", method = RequestMethod.PUT)
		public EmployeeDto  updateEmployee(@RequestBody EmployeeDto dto) {
			return employeeService.updateEmployeeDate(dto);
		}
	
}
