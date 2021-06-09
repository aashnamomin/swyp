package ae.etisalat.swyp.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTION_LOGGER")
public class Transaction_Logger {

	@Override
	public String toString() {
		return "Transaction_Logger [id=" + id + ", transactionId=" + transactionId + ", className=" + className
				+ ", methodName=" + methodName + ", url=" + url + ", requestData=" + requestData + ", responseData="
				+ responseData + ", executionTime=" + executionTime + "]";
	}

	@Id
	@GeneratedValue
	private BigDecimal id;

	private String transactionId;

	private String className;

	private String methodName;
	
	private String url;

	@Lob
	private String requestData;

	@Lob
	private String responseData;

	private long executionTime;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	public String getResponseData() {
		return responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	

}
