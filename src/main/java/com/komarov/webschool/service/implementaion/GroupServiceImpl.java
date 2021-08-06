package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.entity.Group;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.GroupRepository;
import com.komarov.webschool.service.GroupService;
import com.komarov.webschool.service.StudentsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public record GroupServiceImpl(GroupRepository groupRepository, StudentsService studentsService) implements GroupService {

    @Override
    public List<Group> findAll() {
        log.debug("GroupService.findAll()");

        return groupRepository.findAll();
    }

    @Override
    public Group findById(Long id) {
        log.debug("GroupService.findById(id-{})", id);

        return groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Group with id - %d was not found.", id)));
    }

    @Override
    public Group create(Group groupWithoutIdAndStudents) {
        log.debug("GroupService.create({})", groupWithoutIdAndStudents);

        groupWithoutIdAndStudents.setId(null);
        groupWithoutIdAndStudents.setStudents(null);
        return groupRepository.save(groupWithoutIdAndStudents);
    }

    @Override
    public Group update(Long id, Group groupWithoutIdAndStudents) {
        log.debug("GroupService.update(id-{},{})", id, groupWithoutIdAndStudents);

        checkForExists(id);
        groupWithoutIdAndStudents.setId(id);
        groupWithoutIdAndStudents.setStudents(null);
        return groupRepository.save(groupWithoutIdAndStudents);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("GroupService.deleteById(id-{})", id);

        checkForExists(id);
        studentsService.eliminateAllFromGroup(id);
        groupRepository.deleteById(id);
    }

    private void checkForExists(Long id) {
        if(notExists(id)) {
            throw new NotFoundException(String.format("Group with id - %d was not found.", id));
        }
    }

    private boolean notExists(Long id) {
        return !groupRepository.existsById(id);
    }
}
