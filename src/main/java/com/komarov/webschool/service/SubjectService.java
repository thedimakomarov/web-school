package com.komarov.webschool.service;

import com.komarov.webschool.dto.SubjectDto;
import com.komarov.webschool.entity.Subject;

import java.util.List;

public interface SubjectService {
    List<SubjectDto> findAllDto();
    SubjectDto findDtoById(Long id);
    SubjectDto findDtoByName(String subjectName);
    Subject findByName(String subjectName);
    SubjectDto create(SubjectDto subjectDtoWithoutId);
    SubjectDto update(Long id, SubjectDto subjectDtoWithoutId);
    void deleteById(Long id);
}
