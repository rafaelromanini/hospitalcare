package com.hospitalcare.repository;

import com.hospitalcare.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByCpf(String cpf);

    boolean existsByCpf(String cpf);
}
