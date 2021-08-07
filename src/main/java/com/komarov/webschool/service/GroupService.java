package com.komarov.webschool.service;

import com.komarov.webschool.dto.GroupDto;

import java.util.List;

public interface GroupService {
    List<GroupDto> findAll();
    GroupDto findById(Long id);
    GroupDto create(GroupDto groupDtoWithoutIdAndStudents);
    GroupDto update(Long id, GroupDto groupDtoWithoutIdAndStudents);
    void deleteById(Long id);
}
