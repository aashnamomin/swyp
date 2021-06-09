package ae.etisalat.swyp.interceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import ae.etisalat.swyp.aspect.loggerAspect;
import ae.etisalat.swyp.model.Transaction_Logger;
import ae.etisalat.swyp.repository.TxnLoggerRepository;

public class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {

	@Autowired
	private TxnLoggerRepository txnLoggerRepository;

	private static final Logger logger = LogManager.getLogger(RestTemplateHeaderModifierInterceptor.class);

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		final String requestBody = new String(body, StandardCharsets.UTF_8);
		logger.info("service invoked from url " + request.getURI().toString() + " by method "
				+ request.getMethod().toString() + " before executing method");
		logger.info("Request Dto - " + requestBody);

		long start = System.currentTimeMillis();
		ClientHttpResponse response = execution.execute(request, body);

		final String responseBody = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
		
		this.logToH2DB(System.currentTimeMillis() - start, "Interceptor", request.getMethod().toString(), requestBody,
				responseBody, request.getURI().toString());

		logger.info("service invoked from url " + request.getURI().toString() + " by method "
				+ request.getMethod().toString() + " after executing method");
		logger.info("Response Dto - " + responseBody);
		return response;
	}

	private void logToH2DB(long diffTime, String... logDetails) {

		if (logDetails != null) {
			Transaction_Logger txn = new Transaction_Logger();
			txn.setExecutionTime(diffTime);
			txn.setClassName(logDetails[0]);
			txn.setMethodName(logDetails[1]);
			txn.setRequestData(logDetails[2]);
			txn.setResponseData(logDetails[3]);
			txn.setUrl(logDetails[4]);
			txn.setTransactionId(ThreadContext.get("TRANSACTION_ID"));
			txnLoggerRepository.save(txn);
		}
	}
}