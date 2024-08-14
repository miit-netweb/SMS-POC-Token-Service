package Microservice.Token_Service.handler;

import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import Microservice.Token_Service.Dto.ErrorIDGenerator;
import Microservice.Token_Service.Dto.ExceptionResponse;
import Microservice.Token_Service.exception.DbException;
import Microservice.Token_Service.exception.ParserException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ParserException.class)
	public ResponseEntity<ExceptionResponse> handleValidationException(ParserException exception) {
		ExceptionResponse response=new ExceptionResponse();
		response.setErrorid(ErrorIDGenerator.getErrorId());
		response.setErrorcode(exception.getErrorcode());
		response.setMessage(exception.getMessage());
		response.setStatus(exception.getStatus());
		response.setTimestamp(LocalTime.now().toString());
		logger.error("ParserException Response {}",response);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DbException.class)
	public ResponseEntity<ExceptionResponse> handleDbException(DbException exception) {
		ExceptionResponse response=new ExceptionResponse();
		response.setErrorid(ErrorIDGenerator.getErrorId());
		response.setErrorcode(exception.getErrorcode());
		response.setMessage(exception.getMessage());
		response.setStatus(exception.getStatus());
		response.setTimestamp(LocalTime.now().toString());
		logger.error("DbException Response {}",response);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGenericException(Exception ex){
		ExceptionResponse response=new ExceptionResponse();
		response.setErrorid(ErrorIDGenerator.getErrorId());
		response.setErrorcode(500);
		response.setMessage(ex.getMessage());
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		response.setTimestamp(LocalTime.now().toString());
		logger.error("GenericException Response {}",response);
		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
