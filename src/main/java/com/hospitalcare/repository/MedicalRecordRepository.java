package com.hospitalcare.repository;

import com.hospitalcare.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByPatientId(Long patientId);

    @Query("SELECT m FROM MedicalRecord m WHERE LOWER(m.diagnosis) LIKE LOWER(CONCAT(:keyword, '%')) OR LOWER(m.prescription) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<MedicalRecord> searchByKeyword(@Param("keyword") String keyword);
}
