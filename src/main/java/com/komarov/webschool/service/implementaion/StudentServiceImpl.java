package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.entity.Student;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.StudentRepository;
import com.komarov.webschool.service.StudentsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public record StudentServiceImpl(StudentRepository repository) implements StudentsService {

    @Override
    public List<Student> findAll() {
        log.debug("StudentServiceImpl.findAll()");

        return repository.findAll();
    }

    @Override
    public Student findById(Long id) {
        log.debug("StudentService.findById(id-{})", id);

        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Student with id - %d was not found.", id)));
    }

    @Override
    public Student create(Student entityWithoutId) {
        log.debug("StudentService.create({})", entityWithoutId);

        entityWithoutId.setId(null);
        return repository.save(entityWithoutId);
    }

    @Override
    public Student update(Long id, Student entityWithoutId) {
        log.debug("StudentService.update(id-{},{})", id, entityWithoutId);

        checkForExists(id);
        entityWithoutId.setId(id);
        return repository.save(entityWithoutId);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("StudentService.deleteById(id-{})", id);

        checkForExists(id);
        repository.deleteById(id);
    }

    @Override
    public void eliminateAllFromGroup(Long id) {
        repository.eliminateAllFromGroup(id);
    }

    private void checkForExists(Long id) {
        if(notExists(id)) {
            throw new NotFoundException(String.format("Student with id - %d was not found.", id));
        }
    }

    private boolean notExists(Long id) {
        return !repository.existsById(id);
    }
}
