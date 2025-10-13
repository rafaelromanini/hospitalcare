package com.hospitalcare.service;

import com.hospitalcare.dto.DoctorRequestDTO;
import com.hospitalcare.dto.responses.DoctorResponseDTO;
    import com.hospitalcare.exceptions.CpfCannotBeChangedException;
import com.hospitalcare.exceptions.doctor.DoctorAlreadyExistsException;
import com.hospitalcare.exceptions.doctor.DoctorCrmAlreadyExistsException;
import com.hospitalcare.exceptions.doctor.DoctorNotFoundException;
import com.hospitalcare.model.Doctor;
import com.hospitalcare.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository repository;

    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public DoctorResponseDTO create(DoctorRequestDTO dto) {
        validateUniqueFields(dto);

        Doctor doctor = toEntity(dto);
        Doctor saved = repository.save(doctor);
        return toResponse(saved);

    }

    @Transactional(readOnly = true)
    public List<DoctorResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public DoctorResponseDTO findById(Long id) {
        Doctor doctor = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found: id=" + id));
        return toResponse(doctor);
    }

    @Transactional
    public DoctorResponseDTO update(Long id, DoctorRequestDTO dto) {
        Doctor doctor = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found: id=" + id));

        // cpf cannot be changed to one that already exists
        if(dto.cpf() != null && !doctor.getCpf().equals(dto.cpf())) {
            throw new CpfCannotBeChangedException();
        }

        // simple crm duplication check when changing crm
        if (!doctor.getCrm().equals(dto.crm()) && repository.existsByCrm(dto.crm())) {
            throw new DoctorCrmAlreadyExistsException(dto.crm());
        }

        doctor.setName(dto.name());
        doctor.setCpf(dto.cpf());
        doctor.setCrm(dto.crm());
        doctor.setSpecialty(dto.specialty());
        doctor.setPhone(dto.phone());

        return toResponse(repository.save(doctor));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new DoctorNotFoundException(id);
        }
        repository.deleteById(id);
    }

    private void validateUniqueFields(DoctorRequestDTO dto) {
        if (repository.existsByCpf(dto.cpf())) {
            throw new DoctorAlreadyExistsException(dto.cpf());
        }
        if (repository.existsByCrm(dto.crm())) {
            throw new DoctorCrmAlreadyExistsException(dto.crm());
        }
    }

    private Doctor toEntity(DoctorRequestDTO dto) {
        return new Doctor(
                dto.name(),
                dto.cpf(),
                dto.crm(),
                dto.specialty(),
                dto.phone()
        );
    }

    private DoctorResponseDTO toResponse(Doctor dto) {
        return new DoctorResponseDTO(
                dto.getId(),
                dto.getName(),
                dto.getCpf(),
                dto.getCrm(),
                dto.getSpecialty(),
                dto.getPhone()
        );
    }

}
