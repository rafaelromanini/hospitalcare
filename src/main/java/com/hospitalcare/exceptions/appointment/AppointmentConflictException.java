package com.hospitalcare.exceptions.appointment;

public class AppointmentConflictException extends RuntimeException {
  public AppointmentConflictException(String message) {
    super(message);
  }
}
