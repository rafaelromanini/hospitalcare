package com.hospitalcare.exceptions;

public class CpfCannotBeChanged extends RuntimeException {
    public CpfCannotBeChanged(String message) {
        super(message);
    }
}
