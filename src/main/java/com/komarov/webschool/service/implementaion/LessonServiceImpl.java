package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.LessonDto;
import com.komarov.webschool.repository.LessonRepository;
import com.komarov.webschool.service.LessonService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public record LessonServiceImpl(LessonRepository lessonRepository) implements LessonService {

    @Override
    public List<LessonDto> findAll() {
        log.debug("LessonService.findAll()");

        return LessonDto.parse(lessonRepository.findAll());
    }
}
