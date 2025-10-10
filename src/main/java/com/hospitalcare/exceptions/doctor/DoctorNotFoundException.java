package com.hospitalcare.exceptions.doctor;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DoctorNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Doctor not found with ID: %d";

    private final Long doctorId;

    public DoctorNotFoundException(Long doctorId) {
        super(String.format(DEFAULT_MESSAGE, doctorId));
        this.doctorId = doctorId;
    }
}
