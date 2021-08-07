package com.komarov.webschool.controller;

import com.komarov.webschool.dto.TeacherDto;
import com.komarov.webschool.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "teachers")
public record TeacherController(TeacherService service) {

    @GetMapping
    public ResponseEntity<List<TeacherDto>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<TeacherDto> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TeacherDto> create(@Valid @RequestBody TeacherDto teacherDtoWithoutId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(teacherDtoWithoutId));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<TeacherDto> update(@PathVariable Long id, @Valid @RequestBody TeacherDto teacherDtoWithoutId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(id, teacherDtoWithoutId));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
