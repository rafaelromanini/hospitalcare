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

    public Doctor() {}

    public Doctor(String name, String crm, Specialty specialty, String phone) {
        this.name = name;
        this.crm = crm;
        this.specialty = specialty;
        this.phone = phone;
    }
}