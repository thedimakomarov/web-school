package com.komarov.webschool.service;

import com.komarov.webschool.dto.InnerTeacherDto;
import com.komarov.webschool.dto.TeacherDto;
import com.komarov.webschool.entity.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> findAll();
    List<TeacherDto> findAllDto();
    Teacher findById(Long id);
    TeacherDto findDtoById(Long id);
    Teacher findByFullName(String firstName, String lastName);
    TeacherDto findDtoByFullName(String firstName, String lastName);
    Teacher findTeacherByInnerTeacherDto(InnerTeacherDto innerTeacherDto);
    TeacherDto create(TeacherDto teacherDtoWithoutId);
    TeacherDto update(Long id, TeacherDto teacherDtoWithoutId);
    void deleteById(Long id);
}
