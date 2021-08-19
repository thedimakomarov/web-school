package com.komarov.webschool.service;

import com.komarov.webschool.dto.TeacherDto;
import com.komarov.webschool.entity.Teacher;

import java.util.List;

public interface TeacherService {
    List<TeacherDto> findAllDto();
    TeacherDto findDtoById(Long id);
    TeacherDto findDtoByFullName(String firstName, String lastName);
    Teacher findByFullName(String firstName, String lastName);
    TeacherDto create(TeacherDto teacherDtoWithoutId);
    TeacherDto update(Long id, TeacherDto teacherDtoWithoutId);
    void deleteById(Long id);
}
