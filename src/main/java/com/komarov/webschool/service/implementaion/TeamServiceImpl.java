package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.InnerTeamDto;
import com.komarov.webschool.dto.TeamDto;
import com.komarov.webschool.entity.Team;
import com.komarov.webschool.exception.DuplicateException;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.StudentRepository;
import com.komarov.webschool.repository.TeamRepository;
import com.komarov.webschool.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    private static final String NOT_FOUND_ID_MESSAGE = "Team with id - %d was not found. Choose another or create new team with current parameters.";
    private static final String NOT_FOUND_NAME_MESSAGE = "Team with name - '%s' was not found. Choose another or create new team with current parameters.";
    private static final String NOT_FOUND_NOT_ENOUGH_INFO = "Team was not found. Please enter valid id or valid name.";
    private static final String DUPLICATE_MESSAGE = "Team with name - %s already exists. Choose another name for team.";
    private final TeamRepository teamRepository;
    private final StudentRepository studentRepository;

    public TeamServiceImpl(TeamRepository teamRepository, StudentRepository studentRepository) {
        this.teamRepository = teamRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public List<TeamDto> findAllDto() {
        return parse(findAll());
    }

    @Override
    public Team findById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id)));
    }

    @Override
    public TeamDto findDtoById(Long id) {
        return parse(findById(id));
    }

    @Override
    public Team findByName(String teamName) {
        return teamRepository.findByName(teamName)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_NAME_MESSAGE, teamName)));
    }

    @Override
    public TeamDto findDtoByName(String teamName) {
        return parse(findByName(teamName));
    }

    @Override
    public Team findTeamByInnerTeamDto(InnerTeamDto innerTeamDto) {
        Long id = innerTeamDto.getId();
        String name = innerTeamDto.getName();

        if (id != null) {
            return findById(id);
        } else if (name != null) {
            return findByName(name);
        } else {
            throw new NotFoundException(NOT_FOUND_NOT_ENOUGH_INFO);
        }
}

    @Override
    public TeamDto create(TeamDto teamDto) {
        checkForDuplicate(teamDto.getName());

        Team team = prepareForSaving(teamDto);
        return parse(teamRepository.save(team));
    }

    @Override
    public TeamDto update(Long id, TeamDto teamDto) {
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
        checkForExists(id);

        studentRepository.eliminateAllFromTeam(id);
        teamRepository.deleteById(id);
    }

    private void checkForExists(Long id) {
        if (notExists(id)) {
            throw new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id));
        }
    }

    private void checkForDuplicate(String name) {
        if (teamRepository.existsByName(name.toLowerCase())) {
            throw new DuplicateException(String.format(DUPLICATE_MESSAGE, name));
        }
    }

    private boolean notExists(Long id) {
        return !teamRepository.existsById(id);
    }
}
