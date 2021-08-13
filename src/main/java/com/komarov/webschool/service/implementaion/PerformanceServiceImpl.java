package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.PerformanceDto;
import com.komarov.webschool.entity.Performance;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.PerformanceRepository;
import com.komarov.webschool.repository.StudentRepository;
import com.komarov.webschool.repository.TeamRepository;
import com.komarov.webschool.service.PerformanceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public record PerformanceServiceImpl(TeamRepository teamRepository,
                                     StudentRepository studentRepository,
                                     PerformanceRepository performanceRepository) implements PerformanceService {
    private static final String NOT_FOUND_ID_MESSAGE = "Mark with id - %d was not found. Choose another id from the list of existing marks.";
    //TODO: refactor logic

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

        Performance performance = Performance.parse(progressDto);
        performance.setLesson(null);
        performance.setStudent(null);
        return PerformanceDto.parse(performanceRepository.save(performance));
    }

    @Override
    public PerformanceDto update(Long id, PerformanceDto progressDto) {
        log.debug("MarkService.update({id-{},{}})", id, progressDto);

        checkForExists(id);

        Performance performance = Performance.parse(progressDto);
        performance.setLesson(null);
        performance.setStudent(null);
        return PerformanceDto.parse(performanceRepository.save(performance));
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
