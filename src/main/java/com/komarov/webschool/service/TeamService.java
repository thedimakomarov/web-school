package com.komarov.webschool.service;

import com.komarov.webschool.dto.TeamDto;
import com.komarov.webschool.entity.Team;

import java.util.List;

public interface TeamService {
    List<TeamDto> findAllDto();
    TeamDto findDtoById(Long id);
    TeamDto findDtoByName(String teamName);
    Team findByName(String teamName);
    TeamDto create(TeamDto teamDto);
    TeamDto update(Long id, TeamDto teamDto);
    void deleteById(Long id);
}
