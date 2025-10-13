package com.hospitalcare.exceptions.appointment;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppointmentNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Appointment not found with ID: %d";

    private final Long appointmentId;

    public AppointmentNotFoundException(Long appointmentId) {
        super(String.format(DEFAULT_MESSAGE, appointmentId));
        this.appointmentId = appointmentId;
    }
}
