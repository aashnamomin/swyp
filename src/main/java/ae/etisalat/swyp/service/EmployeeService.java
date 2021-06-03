package ae.etisalat.swyp.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ae.etisalat.swyp.dto.EmployeeDto;
import ae.etisalat.swyp.exceptions.InvalidFormatException;
import ae.etisalat.swyp.exceptions.NotFoundException;
import ae.etisalat.swyp.model.Employee;
import ae.etisalat.swyp.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	EmployeeRepository employeeRepository;

	/*
	 * private static List<Employee> empList = new ArrayList<>(); static {
	 * empList.add(new Employee("aashna", "aashna@gmail.com", "1", "5000"));
	 * empList.add(new Employee("ali", "ali@gmail.com", "2", "6000"));
	 * empList.add(new Employee("simran", "simran@gmail.com", "3", "7000")); }
	 */

	public EmployeeDto createEmployee(EmployeeDto empDto) {

		if (empDto.getEmployeeId() == null) {
			throw new InvalidFormatException("Employee id cannot be null!");
		}

		Employee emp = new Employee();
		emp.setEmployeeId(empDto.getEmployeeId());
		emp.setName(empDto.getName());
		emp.setEmail(empDto.getEmail());
		emp.setSalary(empDto.getSalary());
		employeeRepository.save(emp);
		EmployeeDto dto = this.modelMapper.map(emp, EmployeeDto.class);
		// dto.setTransactionId(empDto.getTransactionId());
//		try {
//			TimeUnit.MINUTES.sleep(1);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return dto;
	}

	public EmployeeDto getEmployeeData(String id) {
		Optional<Employee> emp = employeeRepository.findById(id) ;
		if (!emp.isPresent()) {
			throw new NotFoundException("Employee data is not found!");
		}
		EmployeeDto dto = this.modelMapper.map(emp.get(), EmployeeDto.class);
		return dto;
	}

	public EmployeeDto updateEmployeeDate(EmployeeDto empDto)
	{
		Optional<Employee> emp = employeeRepository.findById(empDto.getEmployeeId()) ;
		if (!emp.isPresent()) {
			throw new NotFoundException("Employee data is not found!");
		}
		Employee empDomain = this.modelMapper.map(empDto,Employee.class);
		empDomain = employeeRepository.save(empDomain);
		EmployeeDto dto = this.modelMapper.map(empDomain, EmployeeDto.class);
		return dto;
	}
}
