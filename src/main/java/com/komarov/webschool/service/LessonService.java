package com.komarov.webschool.service;

import com.komarov.webschool.dto.InnerLessonDto;
import com.komarov.webschool.dto.LessonDto;
import com.komarov.webschool.entity.Lesson;

import java.util.List;

public interface LessonService {
    List<Lesson> findAll();
    List<LessonDto> findDtoAll();
    Lesson findById(Long id);
    LessonDto findDtoById(Long id);
    Lesson findByTopicAndTeamAndSubject(String topic, String team, String subject);
    LessonDto findDtoByTopicAndTeamAndSubject(String topic, String team, String subject);
    Lesson findLessonByInnerLessonDto(InnerLessonDto innerLessonDto);
    LessonDto create(LessonDto lessonDtoWithoutId);
    LessonDto update(Long id, LessonDto lessonDtoWithoutId);
    void deleteById(Long id);
}
