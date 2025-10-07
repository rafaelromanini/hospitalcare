package com.hospitalcare.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record PatientRequestDTO(

        @NotBlank(message = "Name is required.")
        @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "Name must contain only letters.")
        String name,

        @NotBlank(message = "CPF is required.")
        @Pattern(
                regexp = "^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$",
                message = "CPF must be in the format 000.000.000-00 or only numbers."
        )
        String cpf,

        @Pattern(
                regexp = "^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$",
                message = "Phone number must be valid, e.g. (11)99999-8888."
        )
        String phone,

        @NotBlank(message = "Address is required.")
        String address,

        @Past(message = "Birth date must be a past date.")
        LocalDate birthDate
) {}
