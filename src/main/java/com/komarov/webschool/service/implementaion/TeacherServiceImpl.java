package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.TeacherDto;
import com.komarov.webschool.entity.Teacher;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.TeacherRepository;
import com.komarov.webschool.service.TeacherService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public record TeacherServiceImpl(TeacherRepository teacherRepository) implements TeacherService {
    private static final String NOT_FOUND_ID_MESSAGE = "Teacher with id - %d was not found. Choose another or create new teacher with current parameters.";
    private static final String NOT_FOUND_FULL_NAME_MESSAGE = "Teacher with firstName - '%s' and lastName - '%s' was not found. Choose another or create new teacher with current parameters.";


    @Override
    public List<TeacherDto> findAll() {
        log.debug("TeacherService.findAll()");

        return TeacherDto.parse(teacherRepository.findAll());
    }

    @Override
    public TeacherDto findById(Long id) {
        log.debug("TeacherService.findById(id-{})", id);

        return TeacherDto.parse(teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id))));
    }

    @Override
    public Teacher findByFullName(String firstName, String lastName) {
        return teacherRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_FULL_NAME_MESSAGE, firstName, lastName)));
    }

    @Override
    public TeacherDto create(TeacherDto teacherDto) {
        log.debug("TeacherService.create({})", teacherDto);

        Teacher teacherWithoutId = prepareForSaving(teacherDto);
        return TeacherDto.parse(teacherRepository.save(teacherWithoutId));
    }

    @Override
    public TeacherDto update(Long id, TeacherDto teacherDto) {
        log.debug("TeacherService.update(id-{},{})", id, teacherDto);

        checkForExists(id);

        Teacher teacherWithoutId = prepareForSaving(teacherDto);
        teacherWithoutId.setId(id);
        return TeacherDto.parse(teacherRepository.save(teacherWithoutId));
    }

    private Teacher prepareForSaving(TeacherDto teacherDtoWithoutId) {
        Teacher teacherWithoutId = Teacher.parse(teacherDtoWithoutId);
        return teacherWithoutId;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("TeacherService.update(id-{})", id);

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
