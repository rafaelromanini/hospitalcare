package com.hospitalcare.exceptions.doctor;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DoctorAlreadyExistsException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Doctor with CPF %s already exists.";

    private final String cpf;

    public DoctorAlreadyExistsException(String cpf) {
        super(String.format(DEFAULT_MESSAGE, cpf));
        this.cpf = cpf;
    }
}
