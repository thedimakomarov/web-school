package com.komarov.webschool.service;

import com.komarov.webschool.dto.InnerTeamDto;
import com.komarov.webschool.dto.TeamDto;
import com.komarov.webschool.entity.Team;

import java.util.List;

public interface TeamService {
    List<Team> findAll();
    List<TeamDto> findAllDto();
    Team findById(Long id);
    TeamDto findDtoById(Long id);
    Team findByName(String teamName);
    TeamDto findDtoByName(String teamName);
    Team findTeamByInnerTeamDto(InnerTeamDto innerTeamDto);
    TeamDto create(TeamDto teamDto);
    TeamDto update(Long id, TeamDto teamDto);
    void deleteById(Long id);
}
