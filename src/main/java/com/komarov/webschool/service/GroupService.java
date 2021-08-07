package com.komarov.webschool.service;

import com.komarov.webschool.entity.Group;

import java.util.List;

public interface GroupService {
    List<Group> findAll();
    Group findById(Long id);
    Group create(Group entityWithoutId);//TODO: change name
    Group update(Long id, Group entityWithoutId);//TODO: change name
    void deleteById(Long id);
}
