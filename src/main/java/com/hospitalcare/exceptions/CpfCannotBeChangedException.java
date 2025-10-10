package com.hospitalcare.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CpfCannotBeChangedException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "CPF cannot be changed";

    public CpfCannotBeChangedException() {
        super(String.format(DEFAULT_MESSAGE));
    }
}
