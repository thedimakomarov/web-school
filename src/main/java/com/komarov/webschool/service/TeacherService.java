package com.komarov.webschool.service;

import com.komarov.webschool.dto.TeacherDto;

import java.util.List;

public interface TeacherService {
    List<TeacherDto> findAll();
    TeacherDto findById(Long id);
    TeacherDto create(TeacherDto teacherDtoWithoutId);
    TeacherDto update(Long id, TeacherDto teacherDtoWithoutId);
    void deleteById(Long id);
}
