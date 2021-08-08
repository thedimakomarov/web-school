package com.komarov.webschool.service;

import com.komarov.webschool.dto.LessonDto;

import java.util.List;

public interface LessonService {
    List<LessonDto> findAll();
}
