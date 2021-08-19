package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.TeamDto;
import com.komarov.webschool.entity.Team;
import com.komarov.webschool.exception.DuplicateException;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.StudentRepository;
import com.komarov.webschool.repository.TeamRepository;
import com.komarov.webschool.service.TeamService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public record TeamServiceImpl(TeamRepository teamRepository, StudentRepository studentRepository) implements TeamService {
    private static final String NOT_FOUND_ID_MESSAGE = "Team with id - %d was not found. Choose another or create new team with current parameters.";
    private static final String NOT_FOUND_NAME_MESSAGE = "Team with name - '%s' was not found. Choose another or create new team with current parameters.";
    private static final String DUPLICATE_MESSAGE = "Team with name - %s already exists. Choose another name for team.";

    @Override
    public List<TeamDto> findAllDto() {
        log.debug("TeamService.findAll()");

        return parse(teamRepository.findAll());
    }

    @Override
    public TeamDto findDtoById(Long id) {
        log.debug("TeamService.findById(id-{})", id);

        return parse(teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id))));
    }

    @Override
    public TeamDto findDtoByName(String teamName) {
        return parse(findByName(teamName));
    }

    @Override
    public Team findByName(String teamName) {
        return teamRepository.findByName(teamName)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_NAME_MESSAGE, teamName)));
    }

    @Override
    public TeamDto create(TeamDto teamDto) {
        log.debug("TeamService.create({})", teamDto);

        checkForDuplicate(teamDto.getName());

        Team team = prepareForSaving(teamDto);
        return parse(teamRepository.save(team));
    }

    @Override
    public TeamDto update(Long id, TeamDto teamDto) {
        log.debug("TeamService.update(id-{},{})", id, teamDto);

        checkForExists(id);
        checkForDuplicate(teamDto.getName());

        Team team = prepareForSaving(teamDto);
        team.setId(id);
        return parse(teamRepository.save(team));
    }

    private Team prepareForSaving(TeamDto teamDto) {
        return parse(teamDto);
    }

    private static Team parse(TeamDto teamDto) {
        return new Team(
                teamDto.getId(),
                teamDto.getName()
        );
    }

    private TeamDto parse(Team team) {
        return new TeamDto(
                team.getId(),
                team.getName()
        );
    }

    private List<TeamDto> parse(List<Team> teams) {
        return teams.stream()
                .map(this::parse)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        log.debug("TeamService.deleteById(id-{})", id);

        checkForExists(id);

        studentRepository.eliminateAllFromTeam(id);
        teamRepository.deleteById(id);
    }

    private void checkForExists(Long id) {
        if(notExists(id)) {
            throw new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id));
        }
    }

    private void checkForDuplicate(String name) {
        if(teamRepository.existsByName(name.toLowerCase())) {
            throw new DuplicateException(String.format(DUPLICATE_MESSAGE, name));
        }
    }

    private boolean notExists(Long id) {
        return !teamRepository.existsById(id);
    }
}
