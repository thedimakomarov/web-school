package com.komarov.webschool.controller;

import com.komarov.webschool.entity.Teacher;
import com.komarov.webschool.service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "teachers")
@AllArgsConstructor
public class TeacherController {
    private final TeacherService service;

    @GetMapping
    public ResponseEntity<List<Teacher>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Teacher> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Teacher> create(@RequestBody Teacher entityWithoutId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(entityWithoutId));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Teacher> update(@PathVariable Long id, @RequestBody Teacher entityWithoutId) {
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
