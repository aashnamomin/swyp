package ae.etisalat.swyp.dto;



public class EmployeeDto {

	private String name;
	
	private String email;
	
	private String employeeId;
	
	private String salary;
	
	//private String transactionId;
	/*
	 * public EmployeeDto() {}
	 * 
	 * EmployeeDto(String transactionId) { super(transactionId); }
	 */
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	/*
	 * public String getTransactionId() { return transactionId; }
	 * 
	 * public void setTransactionId(String transactionId) { this.transactionId =
	 * transactionId; }
	 */
	
	
}
