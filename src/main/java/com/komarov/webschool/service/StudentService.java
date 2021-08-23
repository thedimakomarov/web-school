package com.komarov.webschool.service;

import com.komarov.webschool.dto.InnerStudentDto;
import com.komarov.webschool.dto.StudentDto;
import com.komarov.webschool.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();
    List<StudentDto> findDtoAll();
    Student findById(Long id);
    StudentDto findDtoById(Long id);
    Student findByFullNameAndTeamName(String firstName, String lastName, String teamName);
    StudentDto findDtoByFullNameAndTeamName(String firstName, String lastName, String teamName);
    Student findStudentByInnerStudentDto(InnerStudentDto innerStudentDto);
    StudentDto create(StudentDto studentDtoWithoutId);
    StudentDto update(Long id, StudentDto studentDtoWithoutId);
    void deleteById(Long id);
}
