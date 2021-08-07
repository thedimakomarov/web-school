package com.komarov.webschool.service;

import com.komarov.webschool.entity.Student;

import java.util.List;

public interface StudentsService {
    List<Student> findAll();
    Student findById(Long id);
    Student create(Student entityWithoutId);//TODO: change name
    Student update(Long id, Student entityWithoutId);//TODO: change name
    void deleteById(Long id);
    void eliminateAllFromGroup(Long id);
}
