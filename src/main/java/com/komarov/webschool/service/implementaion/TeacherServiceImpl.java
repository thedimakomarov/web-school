package com.komarov.webschool.service.implementaion;

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

    @Override
    public List<Teacher> findAll() {
        log.debug("TeacherService.findAll()");

        return repository.findAll();
    }

    @Override
    public Teacher findById(Long id) {
        log.debug("TeacherService.findById(id-{})", id);

        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Teacher with id - %d was not found.", id)));
    }

    @Override
    public Teacher create(Teacher entityWithoutId) {
        log.debug("TeacherService.create({})", entityWithoutId);

        entityWithoutId.setId(null);
        return repository.save(entityWithoutId);
    }

    @Override
    public Teacher update(Long id, Teacher entityWithoutId) {
        log.debug("TeacherService.update(id-{},{})", id, entityWithoutId);

        checkForExists(id);
        entityWithoutId.setId(id);
        return repository.save(entityWithoutId);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("TeacherService.update(id-{})", id);

        checkForExists(id);
        repository.deleteById(id);
    }

    private void checkForExists(Long id) {
        if(notExists(id)) {
            throw new NotFoundException(String.format("Teacher with id - %d was not found.", id));
        }
    }

    private boolean notExists(Long id) {
        return !repository.existsById(id);
    }
}
