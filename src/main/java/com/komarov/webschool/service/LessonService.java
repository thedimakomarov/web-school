package com.komarov.webschool.service;

import com.komarov.webschool.dto.LessonDto;

import java.util.List;

public interface LessonService {
    List<LessonDto> findAll();
    LessonDto findById(Long id);
    LessonDto create(LessonDto lessonDtoWithoutId);
    LessonDto update(Long id, LessonDto lessonDtoWithoutId);
    void deleteById(Long id);
}
