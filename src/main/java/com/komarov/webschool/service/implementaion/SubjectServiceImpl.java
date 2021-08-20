package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.SubjectDto;
import com.komarov.webschool.entity.Subject;
import com.komarov.webschool.exception.DuplicateException;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.SubjectRepository;
import com.komarov.webschool.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    private static final String NOT_FOUND_ID_MESSAGE = "Subject with id - %d was not found. Choose another or create new subject with current parameters.";
    private static final String NOT_FOUND_NAME_MESSAGE = "Subject with name - '%s' was not found. Choose another or create new subject with current parameters.";
    private static final String DUPLICATE_MESSAGE = "Subject with name - %s already exists. Choose another name for subject.";
    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<SubjectDto> findAllDto() {
        return parse(subjectRepository.findAll());
    }

    @Override
    public SubjectDto findDtoById(Long id) {
        return parse(subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id))));
    }

    @Override
    public SubjectDto findDtoByName(String subjectName) {
        return parse(findByName(subjectName));
    }

    @Override
    public Subject findByName(String subjectName) {
        return subjectRepository.findByName(subjectName)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_NAME_MESSAGE, subjectName)));
    }

    @Override
    public SubjectDto create(SubjectDto subjectDto) {
        checkForDuplicate(subjectDto.getName());

        Subject subject = prepareForSaving(subjectDto);
        return parse(subjectRepository.save(subject));
    }

    @Override
    public SubjectDto update(Long id, SubjectDto subjectDto) {
        checkForExists(id);
        checkForDuplicate(subjectDto.getName());

        Subject subject = prepareForSaving(subjectDto);
        subject.setId(id);
        return parse(subjectRepository.save(subject));
    }

    private Subject prepareForSaving(SubjectDto subjectDtoWithoutId) {
        return parse(subjectDtoWithoutId);
    }

    private Subject parse(SubjectDto subjectDto) {
        return new Subject(
                subjectDto.getId(),
                subjectDto.getName()
        );
    }

    private SubjectDto parse(Subject subject) {
        return new SubjectDto(
                subject.getId(),
                subject.getName()
        );
    }

    private List<SubjectDto> parse(List<Subject> subjects) {
        return subjects.stream()
                .map(this::parse)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
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
