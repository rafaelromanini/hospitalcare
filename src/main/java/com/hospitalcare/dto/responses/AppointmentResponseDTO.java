package com.hospitalcare.dto.responses;

import com.hospitalcare.model.enums.AppointmentStatus;
import java.time.LocalDateTime;

public record AppointmentResponseDTO(
        Long id,
        String doctorName,
        String patientName,
        String patientCpf,
        LocalDateTime dateTime,
        AppointmentStatus status
) {}
