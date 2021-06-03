package ae.etisalat.swyp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ExceptionResponse> notFoundException(NotFoundException ex) {
		ExceptionResponse errorObj = new ExceptionResponse();
		errorObj.setErrorCode(HttpStatus.NOT_FOUND.value());
		errorObj.setErrorMsg(ex.getMessage());

		return new ResponseEntity<ExceptionResponse>(errorObj, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<ExceptionResponse> invalidFormatException(InvalidFormatException ex) {
		ExceptionResponse errorObj = new ExceptionResponse();
		errorObj.setErrorCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
		errorObj.setErrorMsg(ex.getMessage());

		return new ResponseEntity<ExceptionResponse>(errorObj, HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
