package com.hospitalcare.exceptions.patient;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PatientAlreadyExistsException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "Patient with CPF %s already exists.";

  private final String cpf;

  public PatientAlreadyExistsException(String cpf) {
    super(String.format(DEFAULT_MESSAGE, cpf));
    this.cpf = cpf;
  }
}
