package com.hospitalcare.exceptions.appointment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppointmentConflictException extends RuntimeException {

    private static final String DEFAULT_MESSAGE =
            "Doctor %s already has an appointment scheduled at %s.";

    private final String doctorName;
    private final LocalDateTime dateTime;

    public AppointmentConflictException(String doctorName, LocalDateTime dateTime) {
        super(String.format(DEFAULT_MESSAGE, doctorName, dateTime));
        this.doctorName = doctorName;
        this.dateTime = dateTime;
    }
}
