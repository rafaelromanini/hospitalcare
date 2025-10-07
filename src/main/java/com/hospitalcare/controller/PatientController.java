package com.hospitalcare.controller;

import com.hospitalcare.dto.PatientRequestDTO;
import com.hospitalcare.dto.responses.PatientResponseDTO;
import com.hospitalcare.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientResponseDTO create(@Valid @RequestBody PatientRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<PatientResponseDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PatientResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public PatientResponseDTO update(@PathVariable Long id, @Valid @RequestBody PatientRequestDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
