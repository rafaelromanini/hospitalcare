package com.hospitalcare.infra.exception;

import com.hospitalcare.exceptions.appointment.AppointmentConflictException;
import com.hospitalcare.exceptions.appointment.AppointmentNotFoundException;
import com.hospitalcare.exceptions.doctor.DoctorAlreadyExistsException;
import com.hospitalcare.exceptions.doctor.DoctorNotFoundException;
import com.hospitalcare.exceptions.patient.PatientAlreadyExistsException;
import com.hospitalcare.exceptions.patient.PatientNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ValidationHandler {

    // Internal record to represent validation errors
    record ValidationError(String field, String message) {
        public ValidationError(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    // Validation errors for @Valid annotated request bodies
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ValidationError> handleValidationErrors(MethodArgumentNotValidException e) {
        return e.getFieldErrors()
                .stream()
                .map(ValidationError::new)
                .toList();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleConstraintViolation(ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            String field = violation.getPropertyPath().toString();
            errors.put(field, violation.getMessage());
        }
        return errors;
    }

    // Database integrity violation (e.g., unique constraint violations)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleDataIntegrity(DataIntegrityViolationException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Data integrity violation");
        error.put("message", e.getMostSpecificCause().getMessage());
        return error;
    }

    // Patient
    @ExceptionHandler(PatientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handlePatientNotFound(PatientNotFoundException e) {
        return Map.of("error", "Patient not found", "message", e.getMessage());
    }

    @ExceptionHandler(PatientAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handlePatientExists(PatientAlreadyExistsException e) {
        return Map.of("error", "Duplicated patient", "message", e.getMessage());
    }

    // Doctor
    @ExceptionHandler(DoctorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleDoctorNotFound(DoctorNotFoundException e) {
        return Map.of("error", "Doctor not found", "message", e.getMessage());
    }

    @ExceptionHandler(DoctorAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleDoctorExists(DoctorAlreadyExistsException e) {
        return Map.of("error", "Duplicated doctor", "message", e.getMessage());
    }

    // Appointment
    @ExceptionHandler(AppointmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleAppointmentNotFound(AppointmentNotFoundException e) {
        return Map.of("error", "Appointment not found", "message", e.getMessage());
    }

    @ExceptionHandler(AppointmentConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleAppointmentConflict(AppointmentConflictException e) {
        return Map.of("error", "Scheduling conflict", "message", e.getMessage());
    }

    // Generic handler for any other exceptions
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleGeneric(Exception e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getClass().getSimpleName());
        error.put("message", e.getMessage());
        return error;
    }
}
