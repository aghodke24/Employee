package com.app.exc_handler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice//compulsory class level annotation 
//=@ControolerAdvice=>global exc handler class
//--common interceptor to intercept All excs in all controller + @ResponseBody added 
//implicitly on ret types of all req handling methods
public class GlobalExceptionHandler {
	//method level anno to tell SC,following is an exc handling method:
	//to handle MethodArgumentNotvalidException
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<?>handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
	System.out.println("in method arg invalid+e");
	List<FieldError>fieldErrors = e.getFieldErrors();//list of dto fields having validations errs
	Map<Object, Object>map=fieldErrors.stream().collect(Collectors.toMap(f -> f.getField(), f -> f.getDefaultMessage()));
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
	
}
}
