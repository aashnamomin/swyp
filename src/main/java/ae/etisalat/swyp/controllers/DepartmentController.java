package ae.etisalat.swyp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ae.etisalat.swyp.dto.DepartmentDto;
import ae.etisalat.swyp.service.DepartmentService;

@RestController
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(value = "add/department", method = RequestMethod.POST)
	public DepartmentDto  addDept(@RequestBody DepartmentDto dto ) {
		return departmentService.createDepartment(dto);
	}

}
