package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.*;
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
    private static final String NOT_FOUND_NOT_ENOUGH_INFO = "Lesson was not found. Please enter valid id or valid topic and team and subject.";
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
    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    @Override
    public List<LessonDto> findDtoAll() {
        return parse(findAll());
    }

    @Override
    public Lesson findById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id)));
    }

    @Override
    public LessonDto findDtoById(Long id) {
        return parse(findById(id));
    }

    @Override
    public Lesson findByTopicAndTeamAndSubject(String topic, String team, String subject) {
        Team teamToFindLesson = teamService.findByName(team);
        Subject subjectToFindLesson = subjectService.findByName(subject);

        return lessonRepository.findByTopicAndTeamAndSubject(topic, teamToFindLesson, subjectToFindLesson)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_INNER_LESSON_MESSAGE, topic, teamToFindLesson, subjectToFindLesson)));
    }

    @Override
    public LessonDto findDtoByTopicAndTeamAndSubject(String topic, String team, String subject) {
        return parse(findByTopicAndTeamAndSubject(topic, team, subject));
    }

    @Override
    public Lesson findLessonByInnerLessonDto(InnerLessonDto innerLessonDto) {
        Long id = innerLessonDto.getId();
        String topic = innerLessonDto.getTopic();
        String team = innerLessonDto.getTeamName();
        String subject = innerLessonDto.getSubjectName();

        if(id != null) {
            return findById(id);
        } else if(topic != null && team != null && subject != null){
            return findByTopicAndTeamAndSubject(topic, team, subject);
        } else {
            throw new NotFoundException(NOT_FOUND_NOT_ENOUGH_INFO);
        }
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

    private Lesson prepareForSaving(LessonDto lessonDto) {
        Team team = teamService.findTeamByInnerTeamDto(lessonDto.getTeam());
        Teacher teacher = teacherService.findTeacherByInnerTeacherDto(lessonDto.getTeacher());
        Subject subject = subjectService.findSubjectByInnerSubjectDto(lessonDto.getSubject());

        Lesson lesson = parse(lessonDto);
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
                InnerTeamDto.parse(lesson.getTeam()),
                InnerTeacherDto.parse(lesson.getTeacher()),
                InnerSubjectDto.parse(lesson.getSubject())
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
