package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.SubjectDto;
import com.komarov.webschool.entity.Subject;
import com.komarov.webschool.exception.DuplicateException;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.SubjectRepository;
import com.komarov.webschool.service.SubjectService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public record SubjectServiceImpl(SubjectRepository subjectRepository) implements SubjectService {
    private static final String NOT_FOUND_ID_MESSAGE = "Subject with id - %d was not found. Choose another or create new subject with current parameters.";
    private static final String NOT_FOUND_NAME_MESSAGE = "Subject with name - '%s' was not found. Choose another or create new subject with current parameters.";
    private static final String DUPLICATE_MESSAGE = "Subject with name - %s already exists. Choose another name for subject.";

    @Override
    public List<SubjectDto> findAll() {
        log.debug("SubjectServiceImpl.findAll()");

        return SubjectDto.parse(subjectRepository.findAll());
    }

    @Override
    public SubjectDto findById(Long id) {
        log.debug("SubjectService.findById(id-{})", id);

        return SubjectDto.parse(subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id))));
    }

    @Override
    public Subject findByName(String subjectName) {
        return subjectRepository.findByName(subjectName)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_NAME_MESSAGE, subjectName)));
    }

    @Override
    public SubjectDto create(SubjectDto subjectDtoWithoutId) {
        log.debug("SubjectService.create({})", subjectDtoWithoutId);

        checkForDuplicate(subjectDtoWithoutId.getName());

        Subject subjectWithoutId = Subject.parse(subjectDtoWithoutId);
        return SubjectDto.parse(subjectRepository.save(subjectWithoutId));
    }

    @Override
    public SubjectDto update(Long id, SubjectDto subjectDtoWithoutId) {
        log.debug("SubjectService.update(id-{},{})", id, subjectDtoWithoutId);

        checkForExists(id);
        checkForDuplicate(subjectDtoWithoutId.getName());

        subjectDtoWithoutId.setId(id);
        Subject subjectWithoutId = Subject.parse(subjectDtoWithoutId);
        return SubjectDto.parse(subjectRepository.save(subjectWithoutId));
    }

    @Override
    public void deleteById(Long id) {
        log.debug("SubjectService.deleteById(id-{})", id);

        checkForExists(id);

        subjectRepository.deleteById(id);
    }

    private void checkForExists(Long id) {
        if(notExists(id)) {
            throw new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id));
        }
    }

    private void checkForDuplicate(String name) {
        if(subjectRepository.existsByName(name.toLowerCase())) {
            throw new DuplicateException(String.format(DUPLICATE_MESSAGE, name));
        }
    }

    private boolean notExists(Long id) {
        return !subjectRepository.existsById(id);
    }
}
