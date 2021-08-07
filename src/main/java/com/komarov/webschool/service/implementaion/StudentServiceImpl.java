package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.StudentDto;
import com.komarov.webschool.entity.Group;
import com.komarov.webschool.entity.Student;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.GroupRepository;
import com.komarov.webschool.repository.StudentRepository;
import com.komarov.webschool.service.StudentsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public record StudentServiceImpl(StudentRepository studentRepository, GroupRepository groupRepository) implements StudentsService {
    private static final String NOT_FOUND_ID_MESSAGE = "Student with id - %d was not found. Choose another id from the list of existing students.";
    private static final String NOT_FOUND_GROUP_MESSAGE = "Student with group - %s was not found. Choose another group from the list of existing groups, or create new group with current name.";
    private static final String EXTRA_ID_MESSAGE = "Remove pair with key 'id' from body.";
    private static final String EXTRA_GROUP_MESSAGE = "Remove pair with group's key 'id' from body.";

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

        checkId(studentDtoWithoutId);
        checkGroupId(studentDtoWithoutId);

        Student student = Student.parse(studentDtoWithoutId);
        String groupName = studentDtoWithoutId.getGroup().getName().toLowerCase();
        Group group = groupRepository.findByName(groupName)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_GROUP_MESSAGE, groupName)));
        student.setGroup(group);
        return StudentDto.parse(studentRepository.save(student));
    }

    @Override
    public StudentDto update(Long id, StudentDto studentDtoWithoutId) {
        log.debug("StudentService.update(id-{},{})", id, studentDtoWithoutId);

        checkForExists(id);
        checkId(studentDtoWithoutId);
        checkGroupId(studentDtoWithoutId);

        studentDtoWithoutId.setId(id);
        Student student = Student.parse(studentDtoWithoutId);
        String groupName = studentDtoWithoutId.getGroup().getName().toLowerCase();
        Group group = groupRepository.findByName(groupName)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_GROUP_MESSAGE, groupName)));
        student.setGroup(group);
        return StudentDto.parse(studentRepository.save(student));
    }

    @Override
    public void deleteById(Long id) {
        log.debug("StudentService.deleteById(id-{})", id);

        checkForExists(id);
        studentRepository.deleteById(id);
    }

    @Override
    public void eliminateAllFromGroup(Long id) {
        studentRepository.eliminateAllFromGroup(id);
    }

    private void checkForExists(Long id) {
        if(notExists(id)) {
            throw new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id));
        }
    }

    private void checkId(StudentDto studentDto) {
        if(studentDto.getId() != null) {
            throw new NotFoundException(EXTRA_ID_MESSAGE);
        }
    }

    private void checkGroupId(StudentDto studentDto) {
        if(studentDto.getGroup().getId() != null) {
            throw new NotFoundException(EXTRA_GROUP_MESSAGE);
        }
    }

    private boolean notExists(Long id) {
        return !studentRepository.existsById(id);
    }
}
