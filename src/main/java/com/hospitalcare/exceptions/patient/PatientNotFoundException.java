package com.hospitalcare.exceptions.patient;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PatientNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Patient not found with ID: %d";

    private final Long patientId;

    public PatientNotFoundException(Long patientId) {
        super(String.format(DEFAULT_MESSAGE, patientId));
        this.patientId = patientId;
    }
}
