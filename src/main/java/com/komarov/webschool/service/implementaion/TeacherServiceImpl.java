package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.TeacherDto;
import com.komarov.webschool.entity.Teacher;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.TeacherRepository;
import com.komarov.webschool.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    private static final String NOT_FOUND_ID_MESSAGE = "Teacher with id - %d was not found. Choose another or create new teacher with current parameters.";
    private static final String NOT_FOUND_FULL_NAME_MESSAGE = "Teacher with firstName - '%s' and lastName - '%s' was not found. Choose another or create new teacher with current parameters.";
    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<TeacherDto> findAllDto() {
        return parse(teacherRepository.findAll());
    }

    @Override
    public TeacherDto findDtoById(Long id) {
        return parse(teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id))));
    }

    @Override
    public TeacherDto findDtoByFullName(String firstName, String lastName) {
        return parse(findByFullName(firstName, lastName));
    }

    @Override
    public Teacher findByFullName(String firstName, String lastName) {
        return teacherRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_FULL_NAME_MESSAGE, firstName, lastName)));
    }

    @Override
    public TeacherDto create(TeacherDto teacherDto) {
        Teacher teacherWithoutId = prepareForSaving(teacherDto);
        return parse(teacherRepository.save(teacherWithoutId));
    }

    @Override
    public TeacherDto update(Long id, TeacherDto teacherDto) {
        checkForExists(id);

        Teacher teacherWithoutId = prepareForSaving(teacherDto);
        teacherWithoutId.setId(id);
        return parse(teacherRepository.save(teacherWithoutId));
    }

    private Teacher prepareForSaving(TeacherDto teacherDtoWithoutId) {
        return parse(teacherDtoWithoutId);
    }

    private Teacher parse(TeacherDto teacherDto) {
        return new Teacher(
                teacherDto.getId(),
                teacherDto.getFirstName(),
                teacherDto.getLastName(),
                teacherDto.getMobile()
        );
    }

    private TeacherDto parse(Teacher teacher) {
        return new TeacherDto(
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getMobile()
        );
    }

    private List<TeacherDto> parse(List<Teacher> teachers) {
        return teachers.stream()
                .map(this::parse)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        checkForExists(id);

        teacherRepository.deleteById(id);
    }

    private void checkForExists(Long id) {
        if(notExists(id)) {
            throw new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id));
        }
    }

    private boolean notExists(Long id) {
        return !teacherRepository.existsById(id);
    }
}
