package com.hospitalcare.service;

import com.hospitalcare.dto.PatientRequestDTO;
import com.hospitalcare.dto.responses.PatientResponseDTO;
import com.hospitalcare.exceptions.patient.PatientAlreadyExistsException;
import com.hospitalcare.exceptions.patient.PatientNotFoundException;
import com.hospitalcare.model.Patient;
import com.hospitalcare.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PatientResponseDTO create(PatientRequestDTO dto) {
        if (repository.existsByCpf(dto.cpf())) {
            throw new PatientAlreadyExistsException(dto.cpf());
        }
        Patient patient = toEntity(dto);
        Patient saved = repository.save(patient);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<PatientResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PatientResponseDTO findById(Long id) {
        Patient patient = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found: id=" + id));
        return toResponse(patient);
    }

    @Transactional
    public PatientResponseDTO update(Long id, PatientRequestDTO dto) {
        Patient patient = repository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));

        // simple cpf duplication check when changing cpf
        if (!patient.getCpf().equals(dto.cpf()) && repository.existsByCpf(dto.cpf())) {
            throw new PatientAlreadyExistsException(dto.cpf());
        }

        patient.setName(dto.name());
        patient.setCpf(dto.cpf());
        patient.setPhone(dto.phone());
        patient.setAddress(dto.address());
        patient.setBirthDate(dto.birthDate());
        return toResponse(repository.save(patient));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new PatientNotFoundException(id);
        }
        repository.deleteById(id);
    }

    private Patient toEntity(PatientRequestDTO dto) {
        return new Patient(dto.name(), dto.cpf(), dto.phone(), dto.address(), dto.birthDate());
    }

    private PatientResponseDTO toResponse(Patient patient) {
        return new PatientResponseDTO(
                patient.getId(),
                patient.getName(),
                patient.getCpf(),
                patient.getPhone(),
                patient.getAddress(),
                patient.getBirthDate()
        );
    }
}
