package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.SubjectDto;
import com.komarov.webschool.entity.Subject;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.SubjectRepository;
import com.komarov.webschool.service.SubjectService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public record SubjectServiceImpl(SubjectRepository subjectRepository) implements SubjectService {
    private static final String NOT_FOUND_MESSAGE = "Subject with id - %d was not found. Choose another id from the list of existing subjects.";

    @Override
    public List<SubjectDto> findAll() {
        log.debug("SubjectServiceImpl.findAll()");

        return SubjectDto.parse(subjectRepository.findAll());
    }

    @Override
    public SubjectDto findById(Long id) {
        log.debug("SubjectService.findById(id-{})", id);

        return SubjectDto.parse(subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_MESSAGE, id))));
    }

    @Override
    public SubjectDto create(SubjectDto subjectDtoWithoutId) {
        log.debug("SubjectService.create({})", subjectDtoWithoutId);

        Subject subjectWithoutId = Subject.parse(subjectDtoWithoutId);
        return SubjectDto.parse(subjectRepository.save(subjectWithoutId));
    }

    @Override
    public SubjectDto update(Long id, SubjectDto subjectDtoWithoutId) {
        log.debug("SubjectService.update(id-{},{})", id, subjectDtoWithoutId);

        checkForExists(id);

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
            throw new NotFoundException(String.format(NOT_FOUND_MESSAGE, id));
        }
    }

    private boolean notExists(Long id) {
        return !subjectRepository.existsById(id);
    }
}
