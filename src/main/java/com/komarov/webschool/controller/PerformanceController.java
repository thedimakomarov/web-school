package com.komarov.webschool.controller;

import com.komarov.webschool.dto.PerformanceDto;
import com.komarov.webschool.service.PerformanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "marks")
public record PerformanceController(PerformanceService service) {

    @GetMapping
    public ResponseEntity<List<PerformanceDto>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<PerformanceDto> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PerformanceDto> create(@Valid @RequestBody PerformanceDto progressDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(progressDto));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<PerformanceDto> update(@PathVariable Long id, @Valid @RequestBody PerformanceDto progressDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(id, progressDto));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
