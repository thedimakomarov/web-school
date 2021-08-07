package com.komarov.webschool.controller;

import com.komarov.webschool.dto.StudentDto;
import com.komarov.webschool.service.StudentsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "students")
public record StudentController(StudentsService service) {

    @GetMapping
    public ResponseEntity<List<StudentDto>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<StudentDto> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<StudentDto> create(@RequestBody StudentDto studentDtoWithoutId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(studentDtoWithoutId));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<StudentDto> update(@PathVariable Long id, @RequestBody StudentDto studentDtoWithoutId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(id, studentDtoWithoutId));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<StudentDto> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
