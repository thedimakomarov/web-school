package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.InnerStudentDto;
import com.komarov.webschool.dto.PerformanceDto;
import com.komarov.webschool.entity.*;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.LessonRepository;
import com.komarov.webschool.repository.PerformanceRepository;
import com.komarov.webschool.service.PerformanceService;
import com.komarov.webschool.service.StudentService;
import com.komarov.webschool.service.SubjectService;
import com.komarov.webschool.service.TeamService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public record PerformanceServiceImpl(TeamService teamService,
                                     StudentService studentService,
                                     LessonRepository lessonRepository,
                                     SubjectService subjectService,
                                     PerformanceRepository performanceRepository) implements PerformanceService {
    private static final String NOT_FOUND_ID_MESSAGE = "Mark with id - %d was not found. Choose another id from the list of existing marks.";
    //TODO: move to LessonService
    private static final String NOT_FOUND_LESSON_MESSAGE = "Lesson with topic - '%s', team - '%s' and subject - '%s' was not found. Choose another lesson from the list of existing lessons, or create new lesson with current parameters.";

    @Override
    public List<PerformanceDto> findAll() {
        log.debug("MarkService.findAll()");

        return PerformanceDto.parse(performanceRepository.findAll());
    }

    @Override
    public PerformanceDto findById(Long id) {
        log.debug("MarkService.findById(id-{})", id);

        return PerformanceDto.parse(performanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id))));
    }

    @Override
    public PerformanceDto create(PerformanceDto progressDto) {
        log.debug("MarkService.create({})", progressDto);

        Performance performance = preparePerformanceForSave(progressDto);
        return PerformanceDto.parse(performanceRepository.save(performance));
    }

    @Override
    public PerformanceDto update(Long id, PerformanceDto progressDto) {
        log.debug("MarkService.update({id-{},{}})", id, progressDto);

        checkForExists(id);

        Performance performance = preparePerformanceForSave(progressDto);
        performance.setId(id);
        return PerformanceDto.parse(performanceRepository.save(performance));
    }

    //TODO: add logic to check mark and isPresent
    private Performance preparePerformanceForSave(PerformanceDto progressDto) {
        String topicToFindLesson = progressDto.getLesson().getTopic();//TODO: all this action should be in LessonService
        Team teamToFindLesson = teamService.findByName(progressDto.getLesson().getTeam());//TODO: all this action should be in LessonService
        Subject subjectToFindLesson = subjectService.findByName(progressDto.getLesson().getSubject());//TODO: all this action should be in LessonService
        Lesson lessonForPerformance = getLessonBy(topicToFindLesson, teamToFindLesson, subjectToFindLesson);

        InnerStudentDto studentDto = progressDto.getStudent();
        Student studentForPerformance = studentService.findByFullName(studentDto.getFirstName(), studentDto.getLastName());

        Performance performance = Performance.parse(progressDto);
        performance.setLesson(lessonForPerformance);
        performance.setStudent(studentForPerformance);
        return performance;
    }

    private Lesson getLessonBy(String topic, Team team, Subject subject) {//TODO: move to LessonService
        return lessonRepository.findByTopicAndTeamAndSubject(topic, team, subject)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_LESSON_MESSAGE, topic, team, subject)));
    }

    @Override
    public void deleteById(Long id) {
        log.debug("MarkService.deleteById(id-{})", id);

        checkForExists(id);

        performanceRepository.deleteById(id);
    }

    private void checkForExists(Long id) {
        if(notExists(id)) {
            throw new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id));
        }
    }

    private boolean notExists(Long id) {
        return !performanceRepository.existsById(id);
    }
}
