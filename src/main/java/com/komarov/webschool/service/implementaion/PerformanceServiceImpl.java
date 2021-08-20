package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.InnerLessonDto;
import com.komarov.webschool.dto.InnerStudentDto;
import com.komarov.webschool.dto.PerformanceDto;
import com.komarov.webschool.entity.Lesson;
import com.komarov.webschool.entity.Performance;
import com.komarov.webschool.entity.Student;
import com.komarov.webschool.exception.BreakLogicException;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.PerformanceRepository;
import com.komarov.webschool.service.LessonService;
import com.komarov.webschool.service.PerformanceService;
import com.komarov.webschool.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceServiceImpl implements PerformanceService {
    private static final String NOT_FOUND_ID_MESSAGE = "Performance with id - %d was not found. Choose another or create new performance with current parameters.";
    private static final String NOT_PRESENT_STUDENT_HAS_MARK = "Not present student can have only zero as mark.";
    private final StudentService studentService;
    private final LessonService lessonService;
    private final PerformanceRepository performanceRepository;

    public PerformanceServiceImpl(StudentService studentService, LessonService lessonService, PerformanceRepository performanceRepository) {
        this.studentService = studentService;
        this.lessonService = lessonService;
        this.performanceRepository = performanceRepository;
    }

    @Override
    public List<PerformanceDto> findDtoAll() {
        return parse(performanceRepository.findAll());
    }

    @Override
    public PerformanceDto findDtoById(Long id) {
        return parse(performanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id))));
    }

    //TODO: add logic to check if student becomes for lesson's group
    @Override
    public PerformanceDto create(PerformanceDto progressDto) {
        checkСonsistencyMarkAndPresenting(progressDto);

        Performance performance = prepareForSaving(progressDto);
        return parse(performanceRepository.save(performance));
    }

    //TODO: add logic to check if student becomes for lesson's group
    @Override
    public PerformanceDto update(Long id, PerformanceDto progressDto) {
        checkForExists(id);
        checkСonsistencyMarkAndPresenting(progressDto);

        Performance performance = prepareForSaving(progressDto);
        performance.setId(id);
        return parse(performanceRepository.save(performance));
    }

    private Performance prepareForSaving(PerformanceDto progressDto) {
        Lesson lessonForPerformance = lessonService.findByInnerLesson(progressDto.getLesson());

        InnerStudentDto studentDto = progressDto.getStudent();
        Student studentForPerformance = studentService.findByFullName(studentDto.getFirstName(), studentDto.getLastName());

        Performance performance = parse(progressDto);
        performance.setLesson(lessonForPerformance);
        performance.setStudent(studentForPerformance);
        return performance;
    }

    private Performance parse(PerformanceDto progressDto) {
        return new Performance(
                progressDto.getIsPresent(),
                progressDto.getMark()
        );
    }

    private PerformanceDto parse(Performance performance) {
        return new PerformanceDto(
                performance.getId(),
                InnerStudentDto.parse(performance.getStudent()),
                InnerLessonDto.parse(performance.getLesson()),
                performance.getIsPresent(),
                performance.getMark()
        );
    }

    private List<PerformanceDto> parse(List<Performance> performances) {
        return performances.stream()
                .map(this::parse)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        checkForExists(id);

        performanceRepository.deleteById(id);
    }

    private void checkСonsistencyMarkAndPresenting(PerformanceDto progressDto) {
        boolean isMarkNotZero = progressDto.getMark() != 0;
        boolean isNotPresent = !progressDto.getIsPresent();
        if(isNotPresent && isMarkNotZero) {
            throw new BreakLogicException(NOT_PRESENT_STUDENT_HAS_MARK);
        }
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
