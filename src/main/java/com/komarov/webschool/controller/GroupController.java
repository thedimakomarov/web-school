package com.komarov.webschool.controller;

import com.komarov.webschool.dto.GroupDto;
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
    public ResponseEntity<List<GroupDto>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<GroupDto> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<GroupDto> create(@RequestBody GroupDto groupDtoWithoutIdAndStudents) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(groupDtoWithoutIdAndStudents));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<GroupDto> update(@PathVariable Long id, @RequestBody GroupDto groupDtoWithoutIdAndStudents) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(id, groupDtoWithoutIdAndStudents));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
