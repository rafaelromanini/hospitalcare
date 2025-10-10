package com.hospitalcare.controller;

import com.hospitalcare.dto.DoctorRequestDTO;
import com.hospitalcare.dto.responses.DoctorResponseDTO;
import com.hospitalcare.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService service;

    public DoctorController(DoctorService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorResponseDTO create(@Valid @RequestBody DoctorRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<DoctorResponseDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public DoctorResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public DoctorResponseDTO update(@PathVariable Long id, @Valid @RequestBody DoctorRequestDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

