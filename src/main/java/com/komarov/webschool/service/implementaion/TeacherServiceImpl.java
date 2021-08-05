package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.entity.Teacher;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.TeacherRepository;
import com.komarov.webschool.service.TeacherService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public record TeacherServiceImpl(TeacherRepository teacherRepository) implements TeacherService {

    @Override
    public List<Teacher> findAll() {
        log.debug("TeacherServiceImpl.findAll()");

        return teacherRepository.findAll();
    }

    @Override
    public Teacher findById(Long id) {
        log.debug("TeacherServiceImpl.findById(id-{})", id);

        return teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Teacher with id - %d was not found", id)));
    }

    @Override
    public Teacher create(Teacher teacherWithoutId) {
        log.debug("TeacherServiceImpl.create({})", teacherWithoutId);

        return teacherRepository.save(teacherWithoutId);
    }

    @Override
    public Teacher update(Long id, Teacher teacherWithoutId) {
        log.debug("TeacherServiceImpl.update(id-{},{})", id, teacherWithoutId);

        teacherWithoutId.setId(id);
        return teacherRepository.save(teacherWithoutId);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("TeacherServiceImpl.update(id-{})", id);

        teacherRepository.deleteById(id);
    }
}
