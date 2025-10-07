package com.hospitalcare.dto.responses;

import java.time.LocalDate;

public record PatientResponseDTO(
        Long id,
        String name,
        String cpf,
        String phone,
        String address,
        LocalDate birthDate
) {}
