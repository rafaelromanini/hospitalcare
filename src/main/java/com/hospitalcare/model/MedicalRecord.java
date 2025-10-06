package com.hospitalcare.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "tb_medical_record")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String diagnosis;
    private String prescription;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public MedicalRecord() {}

    public MedicalRecord(Patient patient, String diagnosis, String prescription, String notes) {
        this.patient = patient;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.notes = notes;
        this.date = LocalDate.now();
    }
}