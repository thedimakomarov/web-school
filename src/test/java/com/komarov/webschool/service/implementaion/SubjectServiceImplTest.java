package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.SubjectDto;
import com.komarov.webschool.entity.Subject;
import com.komarov.webschool.exception.DuplicateException;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SubjectServiceImplTest {
    @InjectMocks
    private SubjectServiceImpl subjectService;
    @Mock
    private SubjectRepository subjectRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllDto_void_shouldReturnSubjectsDto() {
        //preparation
        when(subjectRepository.findAll())
                .thenReturn(Arrays.asList(
                        new Subject(1L, "art"),
                        new Subject(2L, "math"))
                );
        List<SubjectDto> expectedSubjectsDto = List.of(
                new SubjectDto(1L, "art"),
                new SubjectDto(2L, "math")
        );

        //action
        List<SubjectDto> foundSubjectsDto = subjectService.findAllDto();

        //inspection
        verify(subjectRepository, times(1)).findAll();
        assertThat(foundSubjectsDto).isEqualTo(expectedSubjectsDto);
    }

    @Test
    void findAllDto_void_shouldReturnVoid() {
        //preparation
        when(subjectRepository.findAll()).thenReturn(Collections.emptyList());
        List<SubjectDto> expectedSubjectsDto = Collections.emptyList();

        //action
        List<SubjectDto> foundSubjectsDto = subjectService.findAllDto();

        //inspection
        verify(subjectRepository, times(1)).findAll();
        assertThat(foundSubjectsDto).isEqualTo(expectedSubjectsDto);
    }

    @Test
    void findDtoById_validId_shouldReturnSubjectDto() {
        //preparation
        Long validId = 1L;
        when(subjectRepository.findById(validId)).thenReturn(Optional.of(new Subject(validId, "art")));
        SubjectDto expectedSubjectDto = new SubjectDto(validId, "art");

        //action
        SubjectDto foundSubjectDto = subjectService.findDtoById(validId);

        //inspection
        verify(subjectRepository, times(1)).findById(validId);
        assertThat(foundSubjectDto).isEqualTo(expectedSubjectDto);
    }

    @Test
    void findDtoById_notValidId_shouldThrowNotFoundException() {
        //preparation
        Long notValidId = 1L;
        when(subjectRepository.findById(notValidId)).thenReturn(Optional.empty());

        //inspection
        assertThrows(NotFoundException.class, () -> subjectService.findDtoById(notValidId));
    }

    @Test
    void findDtoByName_validName_shouldReturnSubjectDto() {
        //preparation
        String validName = "art";
        when(subjectRepository.findByName(validName)).thenReturn(Optional.of(new Subject(1L, validName)));
        SubjectDto expectedSubjectDto = new SubjectDto(1L, validName);

        //action
        SubjectDto foundSubjectDto = subjectService.findDtoByName(validName);

        //inspection
        verify(subjectRepository, times(1)).findByName(validName);
        assertThat(foundSubjectDto).isEqualTo(expectedSubjectDto);
    }

    @Test
    void findDtoByName_notValidName_shouldThrowNotFoundException() {
        //preparation
        String notValidName = "art";
        when(subjectRepository.findByName(notValidName)).thenReturn(Optional.empty());

        //inspection
        assertThrows(NotFoundException.class, () -> subjectService.findDtoByName(notValidName));
    }

    @Test
    void findByName_validName_shouldReturnSubject() {
        //preparation
        Long idCreatedByBd = 1L;
        String validName = "art";
        when(subjectRepository.findByName(validName)).thenReturn(Optional.of(new Subject(idCreatedByBd, validName)));
        Subject expectedSubject = new Subject(idCreatedByBd, validName);

        //action
        Subject foundSubject = subjectService.findByName(validName);

        //inspection
        verify(subjectRepository, times(1)).findByName(validName);
        assertThat(foundSubject).isEqualTo(expectedSubject);
    }

    @Test
    void findByName_notValidName_shouldThrowNotFoundException() {
        //preparation
        String notValidName = "art";
        when(subjectRepository.findByName(notValidName)).thenReturn(Optional.empty());

        //inspection
        assertThrows(NotFoundException.class, () -> subjectService.findByName(notValidName));
    }

    @Test
    void create_validSubjectDto_shouldReturnSubjectDto() {
        //preparation
        Long idCreatedByBd = 1L;
        String validName = "art";
        Subject subjectToSave = new Subject(validName);
        Subject savedSubject = new Subject(idCreatedByBd, validName);
        when(subjectRepository.existsByName(validName)).thenReturn(false);
        when(subjectRepository.save(subjectToSave)).thenReturn(savedSubject);
        SubjectDto validSubjectDto = new SubjectDto(validName);
        SubjectDto expectedSubjectDto = new SubjectDto(idCreatedByBd, validName);

        //action
        SubjectDto createdSubjectDto = subjectService.create(validSubjectDto);

        //inspection
        verify(subjectRepository, times(1)).save(subjectToSave);
        verify(subjectRepository, times(1)).existsByName(validName);
        assertThat(createdSubjectDto).isEqualTo(expectedSubjectDto);
    }

    @Test
    void create_notValidSubjectDtoWithDuplicatedName_shouldThrowDuplicateException() {
        //preparation
        String duplicatedName = "art";
        when(subjectRepository.existsByName(duplicatedName)).thenReturn(true);
        SubjectDto notValidSubjectDto = new SubjectDto(duplicatedName);

        //inspection
        assertThrows(DuplicateException.class, () -> subjectService.create(notValidSubjectDto));
    }

    @Test
    void update_validIdAndValidSubjectDto_shouldReturnSubjectDto() {
        //preparation
        Long validId = 1L;
        String validName = "art";
        Subject subjectToSave = new Subject(validId, validName);
        Subject savedSubject = new Subject(validId, validName);
        when(subjectRepository.existsById(validId)).thenReturn(true);
        when(subjectRepository.existsByName(validName)).thenReturn(false);
        when(subjectRepository.save(subjectToSave)).thenReturn(savedSubject);
        SubjectDto validSubjectDto = new SubjectDto(validName);
        SubjectDto expectedSubjectDto = new SubjectDto(validId, validName);


        //action
        SubjectDto updatedSubjectDto = subjectService.update(validId, validSubjectDto);

        //inspection
        verify(subjectRepository, times(1)).existsById(validId);
        verify(subjectRepository, times(1)).existsByName(validName);
        assertThat(updatedSubjectDto).isEqualTo(expectedSubjectDto);
    }

    @Test
    void update_notValidIdAndValidSubjectDto_shouldThrowNotFoundException() {
        //preparation
        Long notValidId = 1L;
        String validName = "art";
        when(subjectRepository.existsById(notValidId)).thenReturn(false);

        //inspection
        assertThrows(NotFoundException.class, () -> subjectService.update(notValidId, new SubjectDto( validName)));
    }

    @Test
    void update_validIdAndNotValidSubjectDtoWithDuplicatedName_shouldThrowDuplicateException() {
        //preparation
        Long validId = 1L;
        String duplicateName = "art";
        when(subjectRepository.existsById(validId)).thenReturn(true);
        when(subjectRepository.existsByName(duplicateName)).thenReturn(true);

        //inspection
        assertThrows(DuplicateException.class, () -> subjectService.update(validId, new SubjectDto(duplicateName)));
    }

    @Test
    void deleteById_validId_shouldReturnVoid() {
        //preparation
        Long validId = 1L;
        when(subjectRepository.existsById(validId)).thenReturn(true);

        //action
        subjectService.deleteById(validId);

        //inspection
        verify(subjectRepository, times(1)).existsById(validId);
        verify(subjectRepository, times(1)).deleteById(validId);
    }

    @Test
    void deleteById_notValidId_shouldThrowNotFoundException() {
        //preparation
        Long notValidId = 1L;
        when(subjectRepository.existsById(notValidId)).thenReturn(false);

        //inspection
        assertThrows(NotFoundException.class, () -> subjectService.deleteById(notValidId));
    }
}