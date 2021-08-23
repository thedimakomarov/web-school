package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.repository.StudentRepository;
import com.komarov.webschool.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class TeamServiceImplTest {
    @InjectMocks
    private TeamServiceImpl teamService;
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private StudentRepository studentRepository;

    @Test
    void findAll_void_shouldReturnTeams() {
        //preparation
        //action
        //checking
    }

    @Test
    void findAll_void_shouldReturnVoid() {
        //preparation
        //action
        //checking
    }

    @Test
    void findAllDto_void_shouldReturnTeamsDto() {
        //preparation
        //action
        //checking
    }

    @Test
    void findAllDto_void_shouldReturnVoid() {
        //preparation
        //action
        //checking
    }

    @Test
    void findById_validId_shouldReturnTeam() {
        //preparation
        //action
        //checking
    }

    @Test
    void findById_notValidId_shouldThrowNotFoundException() {
        //preparation
        //action - checking
    }

    @Test
    void findDtoById_validId_shouldReturnTeamDto() {
        //preparation
        //action
        //checking
    }

    @Test
    void findDtoById_notValidId_shouldThrowNotFoundException() {
        //preparation
        //action - checking
    }

    @Test
    void findByName_validName_shouldReturnTeam() {
        //preparation
        //action
        //checking
    }

    @Test
    void findByName_notValidName_shouldThrowNotFoundException() {
        //preparation
        //action - checking
    }

    @Test
    void findDtoByName_validName_shouldReturnTeamDto() {
        //preparation
        //action
        //checking
    }

    @Test
    void findDtoByName_notValidName_shouldThrowNotFoundException() {
        //preparation
        //action - checking
    }

    @Test
    void create_validTeamDto_shouldReturnTeamDto() {
        //preparation
        //action
        //checking
    }

    @Test
    void create_notValidTeamDtoWithDuplicatedName_shouldThrowDuplicateException() {
        //preparation
        //action - checking
    }

    @Test
    void update_validIdAndValidTeamDto_shouldReturnTeamDto() {
        //preparation
        //action
        //checking
    }

    @Test
    void update_notValidIdAndValidTeamDto_shouldThrowNotFoundException() {
        //preparation
        //action - checking
    }

    @Test
    void update_validIdAndNotValidTeamDtoWithDuplicatedName_shouldThrowDuplicateException() {
        //preparation
        //action - checking
    }

    @Test
    void deleteById_validId_shouldReturnVoid() {
        //preparation
        //action
        //checking
    }

    @Test
    void deleteById_notValidId_shouldThrowNotFoundException() {
        //preparation
        //action - checking
    }
}