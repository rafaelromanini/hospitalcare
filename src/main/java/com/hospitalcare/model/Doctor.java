package com.hospitalcare.model;

import com.hospitalcare.model.enums.Specialty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    private String phone;
}