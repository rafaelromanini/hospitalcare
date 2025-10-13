package com.hospitalcare.dto.responses;

import com.hospitalcare.model.enums.AppointmentStatus;
import java.time.LocalDateTime;

public record AppointmentResponseDTO(
        Long id,
        Long doctorId,
        Long patientId,
        LocalDateTime dateTime,
        AppointmentStatus status
) {}
