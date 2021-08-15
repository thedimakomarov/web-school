package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.StudentDto;
import com.komarov.webschool.entity.Student;
import com.komarov.webschool.entity.Team;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.StudentRepository;
import com.komarov.webschool.service.StudentService;
import com.komarov.webschool.service.TeamService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public record StudentServiceImpl(StudentRepository studentRepository,
                                 TeamService teamService) implements StudentService {
    private static final String NOT_FOUND_ID_MESSAGE = "Student with id - %d was not found. Choose another or create new student with current parameters.";
    private static final String NOT_FOUND_FULL_NAME_MESSAGE =
            "Student with firstName - '%s' and lastName - '%s' was not found. Choose another or create new student with current parameters.";

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
    public Student findByFullName(String firstName, String lastName) {
        return studentRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_FULL_NAME_MESSAGE, firstName, lastName)));
    }

    @Override
    public StudentDto create(StudentDto studentDtoWithoutId) {
        log.debug("StudentService.create({})", studentDtoWithoutId);

        Student student = prepareForSaving(studentDtoWithoutId);
        return StudentDto.parse(studentRepository.save(student));
    }

    @Override
    public StudentDto update(Long id, StudentDto studentDtoWithoutId) {
        log.debug("StudentService.update(id-{},{})", id, studentDtoWithoutId);

        checkForExists(id);

        Student student = prepareForSaving(studentDtoWithoutId);
        student.setId(id);
        return StudentDto.parse(studentRepository.save(student));
    }

    private Student prepareForSaving(StudentDto studentDtoWithoutId) {
        Team team = teamService.findByName(studentDtoWithoutId.getTeam());

        Student student = Student.parse(studentDtoWithoutId);
        student.setTeam(team);
        return student;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("StudentService.deleteById(id-{})", id);

        checkForExists(id);
        studentRepository.deleteById(id);
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
