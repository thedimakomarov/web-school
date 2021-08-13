package com.komarov.webschool.service;

import com.komarov.webschool.dto.TeamDto;

import java.util.List;

public interface TeamService {
    List<TeamDto> findAll();
    TeamDto findById(Long id);
    TeamDto create(TeamDto teamDto);
    TeamDto update(Long id, TeamDto teamDto);
    void deleteById(Long id);
}
