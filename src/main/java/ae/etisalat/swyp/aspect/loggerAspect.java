package ae.etisalat.swyp.aspect;

import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ae.etisalat.swyp.model.Transaction_Logger;
import ae.etisalat.swyp.repository.TxnLoggerRepository;

@Aspect
@Component
public class loggerAspect {

	@Autowired
	TxnLoggerRepository txnLoggerRepository;

	private static final Logger logger = LogManager.getLogger(loggerAspect.class);

	@Autowired
	private ObjectMapper objectMapper;
	private static final String TRANSACTION_ID = "TRANSACTION_ID";

	@Pointcut(value = "(execution(*  ae.etisalat.swyp.*.*.*(..))) && !within(ae.etisalat.swyp.controllers.*) && !within(ae.etisalat.swyp.exceptions.*) && !within(com.sun.proxy.*)")
	public void mypointCut() {
	}

	@Pointcut(value = "(execution(* ae.etisalat.swyp.controllers.*.*(..))) && !within(com.sun.proxy.*)")
	public void myControllerPointCut() {
	}

	@Around("myControllerPointCut()")
	public Object controllerLogger(ProceedingJoinPoint joinPoint) throws Throwable {

		String methodName = joinPoint.getSignature().getName(),
				className = joinPoint.getTarget().getClass().toString();
		String request = objectMapper.writeValueAsString(joinPoint.getArgs());
		String response = null;

		try (final CloseableThreadContext.Instance ctc = CloseableThreadContext.put(TRANSACTION_ID, generateUniqueId())) {
			if (request != null && request.length() != 0) {
				logger.info("method invoked from " + className + " by method " + methodName + " before executing method");
				logger.info("Request Dto - " + request);
				Object obj = joinPoint.proceed();
				response = objectMapper.writeValueAsString(obj);
				logger.info("method invoked from " + className + " by method " + methodName + " after executing method");
				logger.info("Response Dto - " + response);
				
				//logging to DB
				logToH2DB(className, methodName, request, response);
				
				return obj;
			}
		}

		return null;
	}

	@Around("mypointCut()")
	public Object applicationLogger(ProceedingJoinPoint joinPoint) throws Throwable {

		String methodName = joinPoint.getSignature().getName(),
				className = joinPoint.getTarget().getClass().toString();
		String request = objectMapper.writeValueAsString(joinPoint.getArgs());
		String response = null;
		
		if (request != null && request.length() != 0) {
			logger.info("method invoked from " + className + " by method " + methodName + " before executing method");
			logger.info("Request Dto - " + request);
			Object obj = joinPoint.proceed();
			response = objectMapper.writeValueAsString(obj);
			logger.info("method invoked from " + className + " by method " + methodName + " after executing method");
			logger.info("Response Dto - " + response);
			
			//logging to DB
			logToH2DB(className, methodName, request, response);
			return obj;
		}

		return null;
	}

	@AfterThrowing(value = "(execution(*  ae.etisalat.swyp.service.*.*(..))) ", throwing = "ex")
	public void afterThrowingAdvice(JoinPoint joinPoint, Exception ex) throws Throwable {
		
		 String methodName = joinPoint.getSignature().getName(),
				     className = joinPoint.getTarget().getClass().toString(),
				     request = objectMapper.writeValueAsString(joinPoint.getArgs());
		
		logger.error("After Throwing exception in method:" + joinPoint.getSignature());
		logger.error("Error description", ex);
		
		//logging to DB
		logToH2DB(className, methodName, request, ex.getMessage() + "-" + ex.getStackTrace());
	}

	private  String generateUniqueId() {
		 int hashCode = UUID.randomUUID().toString().hashCode();
		return ("" + hashCode).replaceAll("-", "");
	}
	
	private void logToH2DB( String... logDetails) {
		
		if(logDetails!= null) {
			Transaction_Logger txn = new Transaction_Logger();
			txn.setClassName(logDetails[0]);
			txn.setMethodName(logDetails[1]);
			txn.setRequestData(logDetails[2]);
			txn.setResponseData(logDetails[3]);
			txn.setExecutionTime(new Date());
			txn.setTransactionId(ThreadContext.get(TRANSACTION_ID));
			
			txnLoggerRepository.save(txn);
		}
	}

	
}
