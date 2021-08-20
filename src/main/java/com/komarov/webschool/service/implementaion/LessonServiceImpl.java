package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.InnerLessonDto;
import com.komarov.webschool.dto.InnerTeacherDto;
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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {
    private static final String NOT_FOUND_ID_MESSAGE = "Lesson with id - %d was not found. Choose another or create new lesson with current parameters.";
    private static final String NOT_FOUND_INNER_LESSON_MESSAGE = "Lesson with topic - '%s', team - '%s' and subject - '%s' was not found. Choose another or create new lesson with current parameters.";
    private final LessonRepository lessonRepository;
    private final TeamService teamService;
    private final TeacherService teacherService;
    private final SubjectService subjectService;

    public LessonServiceImpl(LessonRepository lessonRepository, TeamService teamService, TeacherService teacherService, SubjectService subjectService) {
        this.lessonRepository = lessonRepository;
        this.teamService = teamService;
        this.teacherService = teacherService;
        this.subjectService = subjectService;
    }

    @Override
    public List<LessonDto> findDtoAll() {
        return parse(lessonRepository.findAll());
    }

    @Override
    public LessonDto findDtoById(Long id) {
        return parse(lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id))));
    }

    @Override
    public LessonDto findDtoByInnerLesson(InnerLessonDto innerLessonDto) {
        return parse(findByInnerLesson(innerLessonDto));
    }

    @Override
    public Lesson findByInnerLesson(InnerLessonDto innerLessonDto) {
        String topicToFindLesson = innerLessonDto.getTopic();
        Team teamToFindLesson = teamService.findByName(innerLessonDto.getTeam());
        Subject subjectToFindLesson = subjectService.findByName(innerLessonDto.getSubject());

        return lessonRepository.findByTopicAndTeamAndSubject(topicToFindLesson, teamToFindLesson, subjectToFindLesson)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_INNER_LESSON_MESSAGE, topicToFindLesson, teamToFindLesson, subjectToFindLesson)));
    }

    @Override
    public LessonDto create(LessonDto lessonDto) {
        Lesson lesson = prepareForSaving(lessonDto);
        return parse(lessonRepository.save(lesson));
    }

    @Override
    public LessonDto update(Long id, LessonDto lessonDto) {
        checkForExists(id);

        Lesson lesson = prepareForSaving(lessonDto);
        lesson.setId(id);
        return parse(lessonRepository.save(lesson));
    }

    private Lesson prepareForSaving(LessonDto lessonDtoWithoutId) {
        Team team = teamService.findByName(lessonDtoWithoutId.getTeam());
        Teacher teacher = teacherService.findByFullName(lessonDtoWithoutId.getTeacher().getFirstName(), lessonDtoWithoutId.getTeacher().getLastName());
        Subject subject = subjectService.findByName(lessonDtoWithoutId.getSubject());

        Lesson lesson = parse(lessonDtoWithoutId);
        lesson.setTeam(team);
        lesson.setTeacher(teacher);
        lesson.setSubject(subject);
        return lesson;
    }

    private Lesson parse(LessonDto lessonDto) {
        return new Lesson(
                lessonDto.getId(),
                lessonDto.getTopic(),
                lessonDto.getDate());
    }

    public LessonDto parse(Lesson lesson) {
        return new LessonDto(
                lesson.getId(),
                lesson.getTopic(),
                lesson.getDate(),
                lesson.getTeam().getName(),
                InnerTeacherDto.parse(lesson.getTeacher()),
                lesson.getSubject().getName()
        );
    }

    public List<LessonDto> parse(List<Lesson> lessons) {
        return lessons.stream()
                .map(this::parse)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
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
