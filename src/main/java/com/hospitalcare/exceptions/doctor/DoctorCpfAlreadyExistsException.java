package com.hospitalcare.exceptions.doctor;

public class DoctorCpfAlreadyExistsException extends RuntimeException {
  public DoctorCpfAlreadyExistsException(String message) {
    super(message);
  }
}
