package com.komarov.webschool.service;

import com.komarov.webschool.dto.InnerLessonDto;
import com.komarov.webschool.dto.LessonDto;
import com.komarov.webschool.entity.Lesson;

import java.util.List;

public interface LessonService {
    List<LessonDto> findDtoAll();
    LessonDto findDtoById(Long id);
    LessonDto findDtoByInnerLesson(InnerLessonDto innerLessonDto);
    Lesson findByInnerLesson(InnerLessonDto innerLessonDto);
    LessonDto create(LessonDto lessonDtoWithoutId);
    LessonDto update(Long id, LessonDto lessonDtoWithoutId);
    void deleteById(Long id);
}
