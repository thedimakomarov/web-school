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
    private static final String NOT_FOUND_ID_MESSAGE = "Team with id - %d was not found. Choose another id from the list of existing teams.";
    private static final String DUPLICATE_MESSAGE = "Team with name - %s already exists. Choose another name for team.";

    @Override
    public List<TeamDto> findAll() {
        log.debug("TeamService.findAll()");

        return TeamDto.parse(teamRepository.findAll());
    }

    @Override
    public TeamDto findById(Long id) {
        log.debug("TeamService.findById(id-{})", id);

        return TeamDto.parse(teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id))));
    }

    @Override
    public TeamDto create(TeamDto teamDtoWithoutIdAndStudents) {
        log.debug("TeamService.create({})", teamDtoWithoutIdAndStudents);

        checkForDuplicate(teamDtoWithoutIdAndStudents.getName());

        Team team = Team.parse(teamDtoWithoutIdAndStudents);
        return TeamDto.parse(teamRepository.save(team));
    }

    @Override
    public TeamDto update(Long id, TeamDto teamDtoWithoutIdAndStudents) {
        log.debug("TeamService.update(id-{},{})", id, teamDtoWithoutIdAndStudents);

        checkForExists(id);
        checkForDuplicate(teamDtoWithoutIdAndStudents.getName());

        teamDtoWithoutIdAndStudents.setId(id);
        Team team = Team.parse(teamDtoWithoutIdAndStudents);
        return TeamDto.parse(teamRepository.save(team));
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
