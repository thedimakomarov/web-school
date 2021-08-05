package com.komarov.webschool.service;

import com.komarov.webschool.entity.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> findAll();
    Subject findById(Long id);
    Subject create(Subject entityWithoutId);
    Subject update(Long id, Subject entityWithoutId);
    void deleteById(Long id);
}
