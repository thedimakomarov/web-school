package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.StudentDto;
import com.komarov.webschool.entity.Student;
import com.komarov.webschool.entity.Team;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.StudentRepository;
import com.komarov.webschool.repository.TeamRepository;
import com.komarov.webschool.service.StudentsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public record StudentServiceImpl(StudentRepository studentRepository,
                                 TeamRepository teamRepository) implements StudentsService {
    private static final String NOT_FOUND_ID_MESSAGE = "Student with id - %d was not found. Choose another id from the list of existing students.";
    private static final String NOT_FOUND_TEAM_MESSAGE = "Student with team - '%s' was not found. Choose another team from the list of existing teams, or create new team with current name.";

    @Override
    public List<StudentDto> findAll() {
        log.debug("StudentServiceImpl.findAll()");

        return StudentDto.parse(studentRepository.findAll());
    }

    @Override
    public StudentDto findById(Long id) {
        log.debug("StudentService.findById(id-{})", id);

        return StudentDto.parse(studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id))));
    }

    @Override
    public StudentDto create(StudentDto studentDtoWithoutId) {
        log.debug("StudentService.create({})", studentDtoWithoutId);

        Team team = checkIfTeamPresentOrThrowException(studentDtoWithoutId.getTeam());

        Student student = Student.parse(studentDtoWithoutId);
        student.setTeam(team);
        return StudentDto.parse(studentRepository.save(student));
    }

    @Override
    public StudentDto update(Long id, StudentDto studentDtoWithoutId) {
        log.debug("StudentService.update(id-{},{})", id, studentDtoWithoutId);

        checkForExists(id);

        Team team = checkIfTeamPresentOrThrowException(studentDtoWithoutId.getTeam());

        Student student = Student.parse(studentDtoWithoutId);
        student.setId(id);
        student.setTeam(team);
        return StudentDto.parse(studentRepository.save(student));
    }

    @Override
    public void deleteById(Long id) {
        log.debug("StudentService.deleteById(id-{})", id);

        checkForExists(id);
        studentRepository.deleteById(id);
    }

    public Team checkIfTeamPresentOrThrowException(String teamName) {
        if (teamName != null) {
            return teamRepository.findByName(teamName)
                    .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_TEAM_MESSAGE, teamName)));
        }
        return null;
    }

    private void checkForExists(Long id) {
        if (notExists(id)) {
            throw new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id));
        }
    }

    private boolean notExists(Long id) {
        return !studentRepository.existsById(id);
    }
}
