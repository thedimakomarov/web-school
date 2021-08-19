package com.komarov.webschool.controller;

import com.komarov.webschool.dto.TeamDto;
import com.komarov.webschool.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "teams")
public record TeamController(TeamService service) {

    @GetMapping
    public ResponseEntity<List<TeamDto>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllDto());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<TeamDto> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findDtoById(id));
    }

    @PostMapping
    public ResponseEntity<TeamDto> create(@Valid @RequestBody TeamDto teamDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(teamDto));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<TeamDto> update(@PathVariable Long id, @Valid @RequestBody TeamDto teamDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(id, teamDto));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
