package com.komarov.webschool.service;

import com.komarov.webschool.dto.StudentDto;
import com.komarov.webschool.entity.Student;

import java.util.List;

public interface StudentService {
    List<StudentDto> findAll();
    StudentDto findById(Long id);
    Student findByFullName(String firstName, String lastName);
    StudentDto create(StudentDto studentDtoWithoutId);
    StudentDto update(Long id, StudentDto studentDtoWithoutId);
    void deleteById(Long id);
}
