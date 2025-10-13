package com.hospitalcare.exceptions.appointment;

public class AppointmentNotFoundException extends RuntimeException {
  public AppointmentNotFoundException(String message) {
    super(message);
  }
}
