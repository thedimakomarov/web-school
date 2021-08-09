package com.komarov.webschool.controller;

import com.komarov.webschool.dto.LessonDto;
import com.komarov.webschool.service.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "lessons")
public record LessonController(LessonService service) {

    @GetMapping
    public ResponseEntity<List<LessonDto>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<LessonDto> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<LessonDto> create(@Valid @RequestBody LessonDto lessonDtoWithoutId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(lessonDtoWithoutId));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<LessonDto> update(@PathVariable Long id, @Valid @RequestBody LessonDto lessonDtoWithoutId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(id, lessonDtoWithoutId));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<LessonDto> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
