package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.LessonDto;
import com.komarov.webschool.entity.Lesson;
import com.komarov.webschool.entity.Subject;
import com.komarov.webschool.entity.Teacher;
import com.komarov.webschool.entity.Team;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.LessonRepository;
import com.komarov.webschool.repository.SubjectRepository;
import com.komarov.webschool.repository.TeacherRepository;
import com.komarov.webschool.repository.TeamRepository;
import com.komarov.webschool.service.LessonService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public record LessonServiceImpl(LessonRepository lessonRepository,
                                TeamRepository teamRepository,
                                TeacherRepository teacherRepository,
                                SubjectRepository subjectRepository) implements LessonService {
    private static final String NOT_FOUND_ID_MESSAGE = "Lesson with id - %d was not found. Choose another id from the list of existing lessons.";
    private static final String NOT_FOUND_TEAM_MESSAGE = "Lesson with team - '%s' was not found. Choose another team from the list of existing teams, or create new team with current name.";
    private static final String NOT_FOUND_TEACHER_MESSAGE = "Lesson with teacher - '%s' was not found. Choose another teacher from the list of existing teachers, or create new teacher with current name.";
    private static final String NOT_FOUND_SUBJECT_MESSAGE = "Lesson with subject - '%s' was not found. Choose another subject from the list of existing subjects, or create new subject with current name.";


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

        Team team = checkIfTeamPresentOrThrowException(lessonDtoWithoutId.getTeam());
        Teacher teacher = checkIfTeacherPresentOrThrowException(lessonDtoWithoutId.getTeacher().getFirstName(), lessonDtoWithoutId.getTeacher().getLastName());
        Subject subject = checkIfSubjectPresentOrThrowException(lessonDtoWithoutId.getSubject());

        Lesson lesson = Lesson.parse(lessonDtoWithoutId);
        lesson.setTeam(team);
        lesson.setTeacher(teacher);
        lesson.setSubject(subject);
        return LessonDto.parse(lessonRepository.save(lesson));
    }

    @Override
    public LessonDto update(Long id, LessonDto lessonDtoWithoutId) {
        log.debug("LessonService.update(id-{}, {})", id, lessonDtoWithoutId);

        checkForExists(id);
        Team team = checkIfTeamPresentOrThrowException(lessonDtoWithoutId.getTeam());
        Teacher teacher = checkIfTeacherPresentOrThrowException(lessonDtoWithoutId.getTeacher().getFirstName(), lessonDtoWithoutId.getTeacher().getLastName());
        Subject subject = checkIfSubjectPresentOrThrowException(lessonDtoWithoutId.getSubject());

        Lesson lesson = Lesson.parse(lessonDtoWithoutId);
        lesson.setId(id);
        lesson.setTeam(team);
        lesson.setTeacher(teacher);
        lesson.setSubject(subject);
        return LessonDto.parse(lessonRepository.save(lesson));
    }

    @Override
    public void deleteById(Long id) {
        log.debug("LessonService.deleteById(id-{})", id);

        checkForExists(id);
        lessonRepository.deleteById(id);
    }

    public Team checkIfTeamPresentOrThrowException(String team) {
        return teamRepository.findByName(team)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_TEAM_MESSAGE, team)));
    }

    public Teacher checkIfTeacherPresentOrThrowException(String firstName, String lastName) {
        return teacherRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_TEACHER_MESSAGE, firstName + " " + lastName)));
    }

    public Subject checkIfSubjectPresentOrThrowException(String subject) {
        return subjectRepository.findByName(subject)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_SUBJECT_MESSAGE, subject)));
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
