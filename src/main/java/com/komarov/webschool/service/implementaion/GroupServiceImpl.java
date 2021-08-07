package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.GroupDto;
import com.komarov.webschool.entity.Group;
import com.komarov.webschool.exception.DuplicateException;
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
    private static final String NOT_FOUND_ID_MESSAGE = "Group with id - %d was not found. Choose another id from the list of existing groups.";
    private static final String DUPLICATE_MESSAGE = "Group with name - %s already exists. Choose another name for group.";

    @Override
    public List<GroupDto> findAll() {
        log.debug("GroupService.findAll()");

        return GroupDto.parse(groupRepository.findAll());
    }

    @Override
    public GroupDto findById(Long id) {
        log.debug("GroupService.findById(id-{})", id);

        return GroupDto.parse(groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id))));
    }

    @Override
    public GroupDto create(GroupDto groupDtoWithoutIdAndStudents) {
        log.debug("GroupService.create({})", groupDtoWithoutIdAndStudents);

        checkForDuplicate(groupDtoWithoutIdAndStudents.getName());

        Group group = Group.parse(groupDtoWithoutIdAndStudents);
        return GroupDto.parse(groupRepository.save(group));
    }

    @Override
    public GroupDto update(Long id, GroupDto groupDtoWithoutIdAndStudents) {
        log.debug("GroupService.update(id-{},{})", id, groupDtoWithoutIdAndStudents);

        checkForExists(id);

        groupDtoWithoutIdAndStudents.setId(id);
        Group group = Group.parse(groupDtoWithoutIdAndStudents);
        return GroupDto.parse(groupRepository.save(group));
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
            throw new NotFoundException(String.format(NOT_FOUND_ID_MESSAGE, id));
        }
    }

    private void checkForDuplicate(String name) {
        if(groupRepository.existsByName(name.toLowerCase())) {
            throw new DuplicateException(String.format(DUPLICATE_MESSAGE, name));
        }
    }

    private boolean notExists(Long id) {
        return !groupRepository.existsById(id);
    }
}
