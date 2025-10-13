package com.hospitalcare.exceptions.appointment;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppointmentConflictException extends RuntimeException {

    private static final String DEFAULT_MESSAGE =
            "Doctor with ID %d already has an appointment at %s.";

    private final Long doctorId;
    private final LocalDateTime dateTime;

    public AppointmentConflictException(Long doctorId, LocalDateTime dateTime) {
        super(String.format(DEFAULT_MESSAGE, doctorId, dateTime));
        this.doctorId = doctorId;
        this.dateTime = dateTime;
    }
}
