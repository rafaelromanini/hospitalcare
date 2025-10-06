package com.hospitalcare.repository;

import com.hospitalcare.model.Doctor;
import com.hospitalcare.model.enums.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByCrm(String crm);

    boolean existsByCrm(String crm);

    List<Doctor> findBySpecialty(Specialty specialty);

    @Query("SELECT d FROM Doctor d WHERE LOWER(d.name) LIKE LOWER(CONCAT(:name, '%'))")
    List<Doctor> searchByName(@Param("name") String name);
}
