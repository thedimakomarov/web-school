package com.komarov.webschool.controller;

import com.komarov.webschool.entity.Teacher;
import com.komarov.webschool.service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "teachers")
@AllArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<List<Teacher>> findAll() {
        return ResponseEntity
                .status(200)
                .body(teacherService.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Teacher> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(teacherService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Teacher> create(@RequestBody Teacher teacherWithoutId) {
        return ResponseEntity
                .status(201)
                .body(teacherService.create(teacherWithoutId));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Teacher> update(@PathVariable Long id, @RequestBody Teacher teacherWithoutId) {
        return ResponseEntity
                .status(200)
                .body(teacherService.update(id, teacherWithoutId));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        teacherService.deleteById(id);
        return ResponseEntity
                .status(204)
                .build();
    }
}
