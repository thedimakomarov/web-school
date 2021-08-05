package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.entity.Subject;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.SubjectRepository;
import com.komarov.webschool.service.SubjectService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public record SubjectServiceImpl(SubjectRepository repository) implements SubjectService {

    @Override
    public List<Subject> findAll() {
        log.debug("SubjectServiceImpl.findAll()");

        return repository.findAll();
    }

    @Override
    public Subject findById(Long id) {
        log.debug("SubjectService.findById(id-{})", id);

        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Subject with id - %d was not found.", id)));
    }

    @Override
    public Subject create(Subject entityWithoutId) {
        log.debug("SubjectService.create({})", entityWithoutId);

        entityWithoutId.setId(null);
        return repository.save(entityWithoutId);
    }

    @Override
    public Subject update(Long id, Subject entityWithoutId) {
        log.debug("SubjectService.update(id-{},{})", id, entityWithoutId);

        checkForExists(id);
        entityWithoutId.setId(id);
        return repository.save(entityWithoutId);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("SubjectService.deleteById(id-{})", id);

        checkForExists(id);
        repository.deleteById(id);
    }

    private void checkForExists(Long id) {
        if(notExists(id)) {
            throw new NotFoundException(String.format("Subject with id - %d was not found.", id));
        }
    }

    private boolean notExists(Long id) {
        return !repository.existsById(id);
    }
}
