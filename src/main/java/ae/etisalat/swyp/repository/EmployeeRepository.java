package ae.etisalat.swyp.repository;

import org.springframework.data.repository.CrudRepository;

import ae.etisalat.swyp.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, String>   {

}
