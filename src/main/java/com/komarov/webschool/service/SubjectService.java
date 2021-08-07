package com.komarov.webschool.service;

import com.komarov.webschool.dto.SubjectDto;

import java.util.List;

public interface SubjectService {
    List<SubjectDto> findAll();
    SubjectDto findById(Long id);
    SubjectDto create(SubjectDto subjectDtoWithoutId);
    SubjectDto update(Long id, SubjectDto subjectDtoWithoutId);
    void deleteById(Long id);
}
