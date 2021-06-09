package ae.etisalat.swyp.aspect;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import ae.etisalat.swyp.model.Transaction_Logger;
import ae.etisalat.swyp.repository.TxnLoggerRepository;

@Aspect
@Component
public class loggerAspect {

	@Autowired
	private TxnLoggerRepository txnLoggerRepository;

	private static final Logger logger = LogManager.getLogger(loggerAspect.class);

	@Autowired
	private ObjectMapper objectMapper;
	private static final String TRANSACTION_ID = "TRANSACTION_ID";

	@Pointcut(value = "(execution(*  ae.etisalat.swyp.*.*.*(..))) && !within(ae.etisalat.swyp.controllers.*) && !within(ae.etisalat.swyp.exceptions.*) && !within(ae.etisalat.swyp.interceptor.*) && !within(com.sun.proxy.*)")
	public void mypointCut() {
	}

	@Pointcut(value = "(execution(* ae.etisalat.swyp.controllers.*.*(..))) && !within(com.sun.proxy.*)")
	public void myControllerPointCut() {
	}
	
	@Around("myControllerPointCut()")
	public Object controllerLogger(ProceedingJoinPoint joinPoint) throws Throwable {

		String methodName = joinPoint.getSignature().getName(), className = joinPoint.getTarget().getClass().toString();
		String request = objectMapper.writeValueAsString(joinPoint.getArgs());
		String response = null;

		long start = System.currentTimeMillis();

		
		  HttpServletRequest requests = ((ServletRequestAttributes)
		  RequestContextHolder.getRequestAttributes()) .getRequest(); String URL =
		  requests.getRequestURL().toString();
		 

		try (final CloseableThreadContext.Instance ctc = CloseableThreadContext.put(TRANSACTION_ID,
				generateUniqueId())) {
			if (request != null && request.length() != 0) {

				logger.info(
						"method invoked from " + className + " by method " + methodName + " before executing method");
				logger.info("Request Dto - " + request);

				Object obj = joinPoint.proceed();

				long end = System.currentTimeMillis();

				response = objectMapper.writeValueAsString(obj);

				logger.info(
						"method invoked from " + className + " by method " + methodName + " after executing method");
				logger.info("Response Dto - " + response);

				// logging to DB
				logToH2DB(end - start, className, methodName, request, response, URL);

				return obj;
			}
		}

		return null;
	}

	@Around("mypointCut()")
	public Object applicationLogger(ProceedingJoinPoint joinPoint) throws Throwable {

		String methodName = joinPoint.getSignature().getName(), className = joinPoint.getTarget().getClass().toString();
		Object[] args = joinPoint.getArgs();
		
		  HttpServletRequest requests = ((ServletRequestAttributes)
		  RequestContextHolder.getRequestAttributes()) .getRequest(); String URL =
		  requests.getRequestURL().toString();
		 

		String request = objectMapper.writeValueAsString(args);
		String response = null;
		
		long start = System.currentTimeMillis();

		if (request != null && request.length() != 0) {
			logger.info("method invoked from " + className + " by method " + methodName + " before executing method");
			logger.info("Request Dto - " + request);

			Object obj = joinPoint.proceed();

			
			/*
			 * if (className.
			 * equalsIgnoreCase("class ae.etisalat.swyp.integration.WeatherIntegrationService"
			 * )) URL =
			 * requests.getAttribute("javax.servlet.forward.request_uri").toString();
			 */

			long end = System.currentTimeMillis();

			response = objectMapper.writeValueAsString(obj);
			logger.info("method invoked from " + className + " by method " + methodName + " after executing method");
			logger.info("Response Dto - " + response);

			// logging to DB
			logToH2DB(end - start, className, methodName, request, response, URL);
			return obj;
		}

		return null;
	}

	@AfterThrowing(value = "(execution(*  ae.etisalat.swyp.service.*.*(..))) ", throwing = "ex")
	public void afterThrowingAdvice(JoinPoint joinPoint, Exception ex) throws Throwable {

		String methodName = joinPoint.getSignature().getName(), className = joinPoint.getTarget().getClass().toString(),
				request = objectMapper.writeValueAsString(joinPoint.getArgs());

		logger.error("After Throwing exception in method:" + joinPoint.getSignature());
		logger.error("Error description", ex);
		long end = System.currentTimeMillis();
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));

		// logging to DB
		logToH2DB(end, className, methodName, request, errors.toString(), null);
	}

	private String generateUniqueId() {
		int hashCode = UUID.randomUUID().toString().hashCode();
		return ("" + hashCode).replaceAll("-", "");
	}

	public void logToH2DB(long diffTime, String... logDetails) {

		if (logDetails != null) {
			Transaction_Logger txn = new Transaction_Logger();
			txn.setExecutionTime(diffTime);
			txn.setClassName(logDetails[0]);
			txn.setMethodName(logDetails[1]);
			txn.setRequestData(logDetails[2]);
			txn.setResponseData(logDetails[3]);
			txn.setUrl(logDetails[4]);
			txn.setTransactionId(ThreadContext.get(TRANSACTION_ID));

			txnLoggerRepository.save(txn);
		}
	}

}
