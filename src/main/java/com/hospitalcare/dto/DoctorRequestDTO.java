package com.hospitalcare.dto;

import com.hospitalcare.model.enums.Specialty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DoctorRequestDTO(
   @NotBlank(message = "Name is required.")
   @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "Name must contain only letters.")
   String name,

   @NotBlank(message = "CPF is required.")
   @Pattern(
           regexp = "^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$",
           message = "CPF must be in the format 000.000.000-00 or only numbers."
   )
   String cpf,

   @NotBlank(message = "CRM is required.")
   @Pattern(
           regexp = "^[0-9]{9}-[0-9]/[A-Z]{2}$",
           message = "CRM must be in the format 000000000-0/UF"
   )
   String crm,

   @NotNull(message = "Specialty is required.")
   Specialty specialty,

   @Pattern(
           regexp = "^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$",
           message = "Phone number must be valid, e.g. (11)99999-8888."
   )
   String phone

) {}
