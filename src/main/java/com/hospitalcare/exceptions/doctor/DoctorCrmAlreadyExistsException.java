package com.hospitalcare.exceptions.doctor;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class DoctorAlreadyExistsException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "Doctor with CRM %s already exists.";

  private final String crm;

  public DoctorAlreadyExistsException(String crm) {
    super(String.format(DEFAULT_MESSAGE, crm));
    this.crm = crm;
  }
}
