package com.komarov.webschool.service;

import com.komarov.webschool.dto.TeamDto;

import java.util.List;

public interface TeamService {
    List<TeamDto> findAll();
    TeamDto findById(Long id);
    TeamDto create(TeamDto teamDtoWithoutIdAndStudents);
    TeamDto update(Long id, TeamDto teamDtoWithoutIdAndStudents);
    void deleteById(Long id);
}
