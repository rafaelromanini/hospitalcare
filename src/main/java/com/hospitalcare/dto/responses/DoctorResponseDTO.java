package com.hospitalcare.dto.responses;

import com.hospitalcare.model.enums.Specialty;

public record DoctorResponseDTO(
        Long id,
        String name,
        String cpf,
        String crm,
        Specialty specialty,
        String phone
) {}