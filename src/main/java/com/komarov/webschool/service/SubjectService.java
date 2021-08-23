package com.komarov.webschool.service;

import com.komarov.webschool.dto.InnerSubjectDto;
import com.komarov.webschool.dto.SubjectDto;
import com.komarov.webschool.entity.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> findAll();
    List<SubjectDto> findAllDto();
    Subject findById(Long id);
    SubjectDto findDtoById(Long id);
    Subject findByName(String subjectName);
    SubjectDto findDtoByName(String subjectName);
    Subject findSubjectByInnerSubjectDto(InnerSubjectDto innerSubjectDto);
    SubjectDto create(SubjectDto subjectDtoWithoutId);
    SubjectDto update(Long id, SubjectDto subjectDtoWithoutId);
    void deleteById(Long id);
}
