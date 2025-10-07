package com.hospitalcare.hospitalcare.repository;

import com.hospitalcare.hospitalcare.model.Appointment;
import com.hospitalcare.hospitalcare.model.enums.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorId(Long doctorId);

    List<Appointment> findByPatientId(Long patientId);

    boolean existsByDoctorIdAndDateTime(Long doctorId, LocalDateTime dateTime);

    Page<Appointment> findAllByStatus(AppointmentStatus status, Pageable pageable);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.dateTime BETWEEN :start AND :end ORDER BY a.dateTime ASC")
    List<Appointment> findAppointmentsInPeriod(@Param("doctorId") Long doctorId,
                                               @Param("start") LocalDateTime start,
                                               @Param("end") LocalDateTime end);

    @Query("SELECT a FROM Appointment a WHERE a.status = :status ORDER BY a.dateTime ASC")
    List<Appointment> findAllByStatusOrdered(@Param("status") AppointmentStatus status);
}
