package com.komarov.webschool.controller;

import com.komarov.webschool.dto.LessonDto;
import com.komarov.webschool.service.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
