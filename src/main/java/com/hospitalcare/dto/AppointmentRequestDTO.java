package com.hospitalcare.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AppointmentRequestDTO(
        @NotNull Long doctorId,
        @NotNull Long patientId,
        @NotNull @Future LocalDateTime dateTime
) {}
