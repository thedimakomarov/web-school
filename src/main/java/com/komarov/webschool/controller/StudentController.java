package com.komarov.webschool.controller;

import com.komarov.webschool.entity.Student;
import com.komarov.webschool.service.StudentsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "students")
public record StudentController(StudentsService service) {
    //TODO: create StudentDto
    //TODO: add ExtraInformationException

    @GetMapping
    public ResponseEntity<List<Student>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Student> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student entityWithoutId) {//TODO: change name
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(entityWithoutId));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Student> update(@PathVariable Long id, @RequestBody Student entityWithoutId) {//TODO: change name
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(id, entityWithoutId));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Student> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
