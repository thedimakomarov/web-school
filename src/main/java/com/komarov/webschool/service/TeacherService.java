package com.komarov.webschool.service;

import com.komarov.webschool.entity.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> findAll();
    Teacher findById(Long id);
    Teacher create(Teacher entityWithoutId);
    Teacher update(Long id, Teacher entityWithoutId);
    void deleteById(Long id);
}
