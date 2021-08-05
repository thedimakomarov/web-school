package com.komarov.webschool.controller;

import com.komarov.webschool.entity.Subject;
import com.komarov.webschool.service.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "subjects")
@AllArgsConstructor
public class SubjectController {
    private final SubjectService service;

    @GetMapping
    public ResponseEntity<List<Subject>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Subject> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Subject> create(@RequestBody Subject entityWithoutId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(entityWithoutId));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Subject> update(@PathVariable Long id, @RequestBody Subject entityWithoutId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(id, entityWithoutId));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
