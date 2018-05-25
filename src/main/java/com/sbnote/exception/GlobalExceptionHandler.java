package com.sbnote.exception;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.sbnote.model.SbNoteCustomException;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(NullPointerException.class)
	  public final ResponseEntity<?> handleServerExceptions(NullPointerException ex, WebRequest request) {
	    SbNoteCustomException sbNoteException = new SbNoteCustomException();
	    sbNoteException.setCode(HttpStatus.CONFLICT.value());
	    sbNoteException.setMessage(ex.getMessage());
	    sbNoteException.setDetails(request.getDescription(false));
	    log.error("NullPointerException: \n", ex);
	    return new ResponseEntity<SbNoteCustomException>(sbNoteException, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ServletException.class)
	  public final ResponseEntity<?> handleNullPointerException(ServletException ex, WebRequest request) {
	    SbNoteCustomException sbNoteException = new SbNoteCustomException();
	    sbNoteException.setCode(HttpStatus.UNAUTHORIZED.value());
	    sbNoteException.setMessage(ex.getMessage());
	    sbNoteException.setDetails(request.getDescription(false));
	    log.error("ServletException: \n", ex);
	    return new ResponseEntity<SbNoteCustomException>(sbNoteException, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	  public final ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
	    SbNoteCustomException sbNoteException = new SbNoteCustomException();
	    sbNoteException.setCode(HttpStatus.BAD_REQUEST.value());
	    sbNoteException.setMessage("Please enter valid email address.");
	    sbNoteException.setDetails(request.getDescription(false));
	    log.error("ResourceNotFoundException: \n", ex);
	    return new ResponseEntity<SbNoteCustomException>(sbNoteException, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	  public final ResponseEntity<?> handleNoResourceException(ResourceNotFoundException ex, WebRequest request) {
	    SbNoteCustomException sbNoteException = new SbNoteCustomException();
	    sbNoteException.setCode(HttpStatus.NOT_FOUND.value());
	    sbNoteException.setMessage(ex.getErrorMessage());
	    sbNoteException.setDetails(request.getDescription(false));
	    log.error("ResourceNotFoundException: \n", ex);
	    return new ResponseEntity<SbNoteCustomException>(sbNoteException, HttpStatus.NOT_FOUND);
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<SbNoteCustomException> handleAll(Exception ex, WebRequest request) {
		SbNoteCustomException sbNoteException = new SbNoteCustomException();
	    sbNoteException.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	    sbNoteException.setMessage(ex.getLocalizedMessage());
	    sbNoteException.setDetails(request.getDescription(false));
	    log.error("Unhandled exception occurred: \n", ex);
	    return new ResponseEntity<SbNoteCustomException>(sbNoteException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

