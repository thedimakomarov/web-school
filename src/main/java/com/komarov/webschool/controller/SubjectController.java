package com.komarov.webschool.controller;

import com.komarov.webschool.dto.SubjectDto;
import com.komarov.webschool.service.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "subjects")
public record SubjectController(SubjectService service) {

    @GetMapping
    public ResponseEntity<List<SubjectDto>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<SubjectDto> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SubjectDto> create(@Valid @RequestBody SubjectDto subjectDtoWithoutId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(subjectDtoWithoutId));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<SubjectDto> update(@PathVariable Long id, @Valid @RequestBody SubjectDto subjectDtoWithoutId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(id, subjectDtoWithoutId));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
