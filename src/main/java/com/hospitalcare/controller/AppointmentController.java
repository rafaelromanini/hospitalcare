package com.hospitalcare.controller;

import com.hospitalcare.dto.AppointmentRequestDTO;
import com.hospitalcare.dto.responses.AppointmentResponseDTO;
import com.hospitalcare.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponseDTO create(@Valid @RequestBody AppointmentRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<AppointmentResponseDTO> listAll() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public AppointmentResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}/cancel")
    public AppointmentResponseDTO cancel(@PathVariable Long id) {
        return service.cancel(id);
    }

    @PutMapping("/{id}/complete")
    public AppointmentResponseDTO complete(@PathVariable Long id) {
        return service.complete(id);
    }
}
