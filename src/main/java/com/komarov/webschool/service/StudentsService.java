package com.komarov.webschool.service;

import com.komarov.webschool.dto.StudentDto;

import java.util.List;

public interface StudentsService {
    List<StudentDto> findAll();
    StudentDto findById(Long id);
    StudentDto create(StudentDto studentDtoWithoutId);
    StudentDto update(Long id, StudentDto studentDtoWithoutId);
    void deleteById(Long id);
}
