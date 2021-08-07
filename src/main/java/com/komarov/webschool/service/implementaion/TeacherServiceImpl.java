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
public record TeacherServiceImpl(TeacherRepository repository) implements TeacherService {
    private static final String NOT_FOUND_MESSAGE = "Teacher with id - %d was not found. Choose another id from the list of existing teachers.";
    private static final String EXTRA_INFORMATION_MESSAGE = "Remove pair with key 'id' from body.";

    @Override
    public List<TeacherDto> findAll() {
        log.debug("TeacherService.findAll()");

        return TeacherDto.parse(repository.findAll());
    }

    @Override
    public TeacherDto findById(Long id) {
        log.debug("TeacherService.findById(id-{})", id);

        return TeacherDto.parse(repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_MESSAGE, id))));
    }

    @Override
    public TeacherDto create(TeacherDto teacherDtoWithoutId) {
        log.debug("TeacherService.create({})", teacherDtoWithoutId);

        checkId(teacherDtoWithoutId);

        Teacher teacherWithoutId = Teacher.parse(teacherDtoWithoutId);
        return TeacherDto.parse(repository.save(teacherWithoutId));
    }

    @Override
    public TeacherDto update(Long id, TeacherDto teacherDtoWithoutId) {
        log.debug("TeacherService.update(id-{},{})", id, teacherDtoWithoutId);

        checkForExists(id);
        checkId(teacherDtoWithoutId);

        teacherDtoWithoutId.setId(id);
        Teacher teacherWithoutId = Teacher.parse(teacherDtoWithoutId);
        return TeacherDto.parse(repository.save(teacherWithoutId));
    }

    @Override
    public void deleteById(Long id) {
        log.debug("TeacherService.update(id-{})", id);

        checkForExists(id);
        repository.deleteById(id);
    }

    private void checkForExists(Long id) {
        if(notExists(id)) {
            throw new NotFoundException(String.format(NOT_FOUND_MESSAGE, id));
        }
    }

    private void checkId(TeacherDto teacherDto) {
        if(teacherDto.getId() != null) {
            throw new NotFoundException(EXTRA_INFORMATION_MESSAGE);
        }
    }

    private boolean notExists(Long id) {
        return !repository.existsById(id);
    }
}
