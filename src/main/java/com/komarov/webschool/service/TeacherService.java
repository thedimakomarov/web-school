package com.komarov.webschool.service;

import com.komarov.webschool.entity.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> findAll();
    Teacher findById(Long id);
    Teacher create(Teacher teacherWithoutId);
    Teacher update(Long id, Teacher teacherWithoutId);
    void deleteById(Long id);
}
