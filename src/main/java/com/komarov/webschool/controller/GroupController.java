package com.komarov.webschool.controller;

import com.komarov.webschool.entity.Group;
import com.komarov.webschool.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "groups")
public record GroupController(GroupService service) {
    //TODO: create GroupDto
    //TODO: add ExtraInformationException

    @GetMapping
    public ResponseEntity<List<Group>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Group> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Group> create(@RequestBody Group entityWithoutId) {//TODO: change name
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(entityWithoutId));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Group> update(@PathVariable Long id, @RequestBody Group entityWithoutId) {//TODO: change name
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
