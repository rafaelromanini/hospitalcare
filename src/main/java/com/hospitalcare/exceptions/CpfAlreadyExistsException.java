package com.hospitalcare.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CpfAlreadyExistsException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "CPF %s already exists in the system.";

  private final String cpf;

  public CpfAlreadyExistsException(String cpf) {
    super(String.format(DEFAULT_MESSAGE, cpf));
    this.cpf = cpf;
  }
}
