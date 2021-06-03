package ae.etisalat.swyp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ae.etisalat.swyp.dto.DepartmentDto;
import ae.etisalat.swyp.model.Department;

@Service
public class DepartmentService {

	@Autowired
	private ModelMapper modelMapper;

	public DepartmentDto createDepartment(DepartmentDto dpDto) {

		

		Department dp = new Department();
		dp.setDepartmentId(dpDto.getDepartmentId());
		dp.setDepartmentName(dpDto.getDepartmentName());
		DepartmentDto dto = this.modelMapper.map(dp, DepartmentDto.class);
		return dto;
	}
}
