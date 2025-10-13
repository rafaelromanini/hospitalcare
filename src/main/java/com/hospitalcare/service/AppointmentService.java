package com.hospitalcare.service;

import com.hospitalcare.dto.AppointmentRequestDTO;
import com.hospitalcare.dto.responses.AppointmentResponseDTO;
import com.hospitalcare.exceptions.patient.PatientNotFoundException;
import com.hospitalcare.exceptions.appointment.AppointmentConflictException;
import com.hospitalcare.exceptions.appointment.AppointmentNotFoundException;
import com.hospitalcare.exceptions.doctor.DoctorNotFoundException;
import com.hospitalcare.model.Appointment;
import com.hospitalcare.model.Doctor;
import com.hospitalcare.model.Patient;
import com.hospitalcare.model.enums.AppointmentStatus;
import com.hospitalcare.repository.AppointmentRepository;
import com.hospitalcare.repository.DoctorRepository;
import com.hospitalcare.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorRepository doctorRepository,
                              PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional
    public AppointmentResponseDTO create(AppointmentRequestDTO dto) {
        Doctor doctor = doctorRepository.findById(dto.doctorId())
                .orElseThrow(() -> new DoctorNotFoundException(dto.doctorId()));
        Patient patient = patientRepository.findById(dto.patientId())
                .orElseThrow(() -> new PatientNotFoundException(dto.patientId()));

        // Business rule: prevent double booking for the same doctor/dateTime
        LocalDateTime start = dto.dateTime().minusMinutes(29);
        LocalDateTime end = dto.dateTime().plusMinutes(29);

        if (appointmentRepository.hasConflictWithinRange(doctor.getId(), start, end)) {
            throw new AppointmentConflictException(doctor.getName(), dto.dateTime());
        }


        Appointment appt = new Appointment(doctor, patient, dto.dateTime()); // sets SCHEDULED by constructor
        Appointment saved = appointmentRepository.save(appt);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> listAll() {
        return appointmentRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AppointmentResponseDTO findById(Long id) {
        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
        return toResponse(appt);
    }

    @Transactional
    public AppointmentResponseDTO cancel(Long id) {
        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
        appt.setStatus(AppointmentStatus.CANCELED);
        return toResponse(appointmentRepository.save(appt));
    }

    @Transactional
    public AppointmentResponseDTO complete(Long id) {
        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
        appt.setStatus(AppointmentStatus.COMPLETED);
        return toResponse(appointmentRepository.save(appt));
    }

    private AppointmentResponseDTO toResponse(Appointment a) {
        return new AppointmentResponseDTO(
                a.getId(),
                a.getDoctor().getName(),
                a.getPatient().getName(),
                a.getPatient().getCpf(),
                a.getDateTime(),
                a.getStatus()
        );
    }
}
