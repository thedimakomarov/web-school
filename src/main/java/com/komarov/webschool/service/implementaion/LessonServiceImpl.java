package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.LessonDto;
import com.komarov.webschool.entity.Lesson;
import com.komarov.webschool.entity.Subject;
import com.komarov.webschool.entity.Teacher;
import com.komarov.webschool.entity.Team;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.LessonRepository;
import com.komarov.webschool.service.LessonService;
import com.komarov.webschool.service.SubjectService;
import com.komarov.webschool.service.TeacherService;
import com.komarov.webschool.service.TeamService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public record LessonServiceImpl(LessonRepository lessonRepository,
                                TeamService teamService,
                                TeacherService teacherService,
                                SubjectService subjectService) implements LessonService {
    private static final String NOT_FOUND_ID_MESSAGE = "Lesson with id - %d was not found. Choose another id from the list of existing lessons.";

    @Override
    public List<LessonDto> findAll() {
        log.debug("LessonService.findAll()");

        return LessonDto.parse(lessonRepository.findAll());
    }

    @Override
    public LessonDto findById(Long id) {
        log.debug("LessonService.findById(id-{})", id);

        return LessonDto.parse(lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id))));
    }

    @Override
    public LessonDto create(LessonDto lessonDtoWithoutId) {
        log.debug("LessonService.create({})", lessonDtoWithoutId);

        Lesson lesson = prepareLessonForSave(lessonDtoWithoutId);
        return LessonDto.parse(lessonRepository.save(lesson));
    }

    @Override
    public LessonDto update(Long id, LessonDto lessonDtoWithoutId) {
        log.debug("LessonService.update(id-{}, {})", id, lessonDtoWithoutId);

        checkForExists(id);

        Lesson lesson = prepareLessonForSave(lessonDtoWithoutId);
        lesson.setId(id);
        return LessonDto.parse(lessonRepository.save(lesson));
    }

    private Lesson prepareLessonForSave(LessonDto lessonDtoWithoutId) {
        Team team = teamService.findByName(lessonDtoWithoutId.getTeam());
        Teacher teacher = teacherService.findByFullName(lessonDtoWithoutId.getTeacher().getFirstName(), lessonDtoWithoutId.getTeacher().getLastName());
        Subject subject = subjectService.findByName(lessonDtoWithoutId.getSubject());

        Lesson lesson = Lesson.parse(lessonDtoWithoutId);
        lesson.setTeam(team);
        lesson.setTeacher(teacher);
        lesson.setSubject(subject);
        return lesson;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("LessonService.deleteById(id-{})", id);

        checkForExists(id);
        lessonRepository.deleteById(id);
    }

    private void checkForExists(Long id) {
        if (notExists(id)) {
            throw new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id));
        }
    }

    private boolean notExists(Long id) {
        return !lessonRepository.existsById(id);
    }
}
