package com.komarov.webschool.service.implementaion;

import com.komarov.webschool.dto.InnerTeamDto;
import com.komarov.webschool.dto.TeamDto;
import com.komarov.webschool.entity.Team;
import com.komarov.webschool.exception.DuplicateException;
import com.komarov.webschool.exception.NotFoundException;
import com.komarov.webschool.repository.StudentRepository;
import com.komarov.webschool.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TeamServiceImplTest {
    @InjectMocks
    private TeamServiceImpl teamService;
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_void_shouldReturnTeams() {
        //preparation
        when(teamRepository.findAll())
                .thenReturn(Arrays.asList(
                        new Team(1L, "g1"),
                        new Team(2L, "g2"))
                );
        List<Team> expectedTeams = List.of(
           new Team(1L, "g1"),
           new Team(2L, "g2")
        );

        //action
        List<Team> foundTeams = teamService.findAll();

        //checking
        verify(teamRepository, times(1)).findAll();
        assertThat(foundTeams).isEqualTo(expectedTeams);
    }

    @Test
    void findAll_void_shouldReturnVoid() {
        //preparation
        when(teamRepository.findAll()).thenReturn(Collections.emptyList());
        List<Team> expectedTeams = Collections.emptyList();

        //action
        List<Team> foundTeams = teamService.findAll();

        //checking
        verify(teamRepository, times(1)).findAll();
        assertThat(foundTeams).isEqualTo(expectedTeams);
    }

    @Test
    void findAllDto_void_shouldReturnTeamsDto() {
        //preparation
        when(teamRepository.findAll())
                .thenReturn(Arrays.asList(
                        new Team(1L, "g1"),
                        new Team(2L, "g2"))
                );
        List<TeamDto> expectedTeamsDto = List.of(
                new TeamDto(1L, "g1"),
                new TeamDto(2L, "g2")
        );

        //action
        List<TeamDto> foundTeamsDto = teamService.findAllDto();

        //checking
        verify(teamRepository, times(1)).findAll();
        assertThat(foundTeamsDto).isEqualTo(expectedTeamsDto);
    }

    @Test
    void findAllDto_void_shouldReturnVoid() {
        //preparation
        when(teamRepository.findAll()).thenReturn(Collections.emptyList());
        List<TeamDto> expectedTeamsDto = Collections.emptyList();

        //action
        List<TeamDto> foundTeamsDto = teamService.findAllDto();

        //checking
        verify(teamRepository, times(1)).findAll();
        assertThat(foundTeamsDto).isEqualTo(expectedTeamsDto);
    }

    @Test
    void findById_validId_shouldReturnTeam() {
        //preparation
        Long validId = 1L;
        when(teamRepository.findById(validId)).thenReturn(Optional.of(new Team(validId, "g1")));
        Team expectedTeam = new Team(validId, "g1");

        //action
        Team foundTeam = teamService.findById(validId);

        //checking
        verify(teamRepository, times(1)).findById(validId);
        assertThat(foundTeam).isEqualTo(expectedTeam);
    }

    @Test
    void findById_notValidId_shouldThrowNotFoundException() {
        //preparation
        Long notValidId = 1L;
        when(teamRepository.findById(notValidId)).thenReturn(Optional.empty());

        //action - checking
        assertThrows(NotFoundException.class, () -> teamService.findById(notValidId));
    }

    @Test
    void findDtoById_validId_shouldReturnTeamDto() {
        //preparation
        Long validId = 1L;
        when(teamRepository.findById(validId)).thenReturn(Optional.of(new Team(validId, "g1")));
        TeamDto expectedTeamDto = new TeamDto(validId, "g1");

        //action
        TeamDto foundTeamDto = teamService.findDtoById(validId);

        //checking
        verify(teamRepository, times(1)).findById(validId);
        assertThat(foundTeamDto).isEqualTo(expectedTeamDto);
    }

    @Test
    void findDtoById_notValidId_shouldThrowNotFoundException() {
        //preparation
        Long notValidId = 1L;
        when(teamRepository.findById(notValidId)).thenReturn(Optional.empty());

        //action - checking
        assertThrows(NotFoundException.class, () -> teamService.findDtoById(notValidId));
    }

    @Test
    void findByName_validName_shouldReturnTeam() {
        //preparation
        Long idCreatedByBd = 1L;
        String validName = "g1";
        when(teamRepository.findByName(validName)).thenReturn(Optional.of(new Team(idCreatedByBd, validName)));
        Team expectedTeam = new Team(idCreatedByBd, validName);

        //action
        Team foundTeam = teamService.findByName(validName);

        //checking
        verify(teamRepository, times(1)).findByName(validName);
        assertThat(foundTeam).isEqualTo(expectedTeam);
    }

    @Test
    void findByName_notValidName_shouldThrowNotFoundException() {
        //preparation
        String notValidName = "g1";
        when(teamRepository.findByName(notValidName)).thenReturn(Optional.empty());

        //action - checking
        assertThrows(NotFoundException.class, () -> teamService.findByName(notValidName));
    }

    @Test
    void findDtoByName_validName_shouldReturnTeamDto() {
        //preparation
        Long id = 1L;
        String validName = "g1";
        when(teamRepository.findByName(validName)).thenReturn(Optional.of(new Team(id, validName)));
        TeamDto expectedTeamDto = new TeamDto(id, validName);

        //action
        TeamDto foundTeamDto = teamService.findDtoByName(validName);

        //checking
        verify(teamRepository, times(1)).findByName(validName);
        assertThat(foundTeamDto).isEqualTo(expectedTeamDto);
    }

    @Test
    void findDtoByName_notValidName_shouldThrowNotFoundException() {
        //preparation
        String notValidName = "g1";
        when(teamRepository.findByName(notValidName)).thenReturn(Optional.empty());

        //action - checking
        assertThrows(NotFoundException.class, () -> teamService.findDtoByName(notValidName));
    }

    @Test
    void findTeamByInnerTeamDto_innerTeamDtoWithId_shouldReturnTeam() {
        //preparation
        Long id = 1L;
        String name = "g1";
        InnerTeamDto innerTeamDtoWithId = new InnerTeamDto(id, null);
        Team foundTeam = new Team(id, name);
        when(teamRepository.findById(id)).thenReturn(Optional.of(foundTeam));
        Team expectedTeam = new Team(id, name);

        //action
        Team foundTeamByInnerTeamDto = teamService.findTeamByInnerTeamDto(innerTeamDtoWithId);

        //checking
        verify(teamRepository, times(1)).findById(id);
        assertThat(foundTeamByInnerTeamDto).isEqualTo(expectedTeam);
    }

    @Test
    void findTeamByInnerTeamDto_innerTeamDtoWithName_shouldReturnTeam() {
        //preparation
        Long id = 1L;
        String name = "g1";
        InnerTeamDto innerTeamDtoWithId = new InnerTeamDto(null, name);
        Team foundTeam = new Team(id, name);
        when(teamRepository.findByName(name)).thenReturn(Optional.of(foundTeam));
        Team expectedTeam = new Team(id, name);

        //action
        Team foundTeamByInnerTeamDto = teamService.findTeamByInnerTeamDto(innerTeamDtoWithId);

        //checking
        verify(teamRepository, times(1)).findByName(name);
        assertThat(foundTeamByInnerTeamDto).isEqualTo(expectedTeam);
    }

    @Test
    void findTeamByInnerTeamDto_innerTeamDtoWithWrongFormattedName_shouldReturnTeam() {
        //preparation
        Long id = 1L;
        String wrongFormattedName = "G1";
        String rightFormattedName = "g1";
        InnerTeamDto innerTeamDtoWithId = new InnerTeamDto(null, wrongFormattedName);
        Team foundTeam = new Team(id, rightFormattedName);
        when(teamRepository.findByName(rightFormattedName)).thenReturn(Optional.of(foundTeam));
        Team expectedTeam = new Team(id, rightFormattedName);

        //action
        Team foundTeamByInnerTeamDto = teamService.findTeamByInnerTeamDto(innerTeamDtoWithId);

        //checking
        verify(teamRepository, times(1)).findByName(rightFormattedName);
        assertThat(foundTeamByInnerTeamDto).isEqualTo(expectedTeam);
    }

    @Test
    void findTeamByInnerTeamDto_innerTeamDtoWithoutAnything_shouldThrowNotFoundException() {
        //preparation
        InnerTeamDto innerTeamDtoWithId = new InnerTeamDto(null, null);

        //action - checking
        assertThrows(NotFoundException.class, () -> teamService.findTeamByInnerTeamDto(innerTeamDtoWithId));
    }

    @Test
    void create_validTeamDto_shouldReturnTeamDto() {
        //preparation
        Long idCreatedByBd = 1L;
        String validName = "g1";
        Team teamToSave = new Team(validName);
        Team savedTeam = new Team(idCreatedByBd, validName);
        when(teamRepository.existsByName(validName)).thenReturn(false);
        when(teamRepository.save(teamToSave)).thenReturn(savedTeam);
        TeamDto validTeamDto = new TeamDto(validName);
        TeamDto expectedTeamDto = new TeamDto(idCreatedByBd, validName);

        //action
        TeamDto createdTeamDto = teamService.create(validTeamDto);

        //checking
        verify(teamRepository, times(1)).existsByName(validName);
        verify(teamRepository, times(1)).save(teamToSave);
        assertThat(createdTeamDto).isEqualTo(expectedTeamDto);
    }

    @Test
    void create_validTeamDtoWithWrongFormat_shouldReturnTeamDtoWithRightFormat() {
        //preparation
        Long idCreatedByBd = 1L;
        String validWrongFormattedName = "G1";
        String validRightFormattedName = "g1";
        Team teamToSave = new Team(validRightFormattedName);
        Team savedTeam = new Team(idCreatedByBd, validRightFormattedName);
        when(teamRepository.existsByName(validRightFormattedName)).thenReturn(false);
        when(teamRepository.save(teamToSave)).thenReturn(savedTeam);
        TeamDto validTeamDtoWithWrongFormat = new TeamDto(validWrongFormattedName);
        TeamDto expectedTeamDto = new TeamDto(idCreatedByBd, validRightFormattedName);

        //action
        TeamDto createdTeamDto = teamService.create(validTeamDtoWithWrongFormat);

        //checking
        verify(teamRepository, times(1)).existsByName(validRightFormattedName);
        verify(teamRepository, times(1)).save(teamToSave);
        assertThat(createdTeamDto).isEqualTo(expectedTeamDto);
    }

    @Test
    void create_notValidTeamDtoWithDuplicatedName_shouldThrowDuplicateException() {
        //preparation
        String duplicatedName = "g1";
        when(teamRepository.existsByName(duplicatedName)).thenReturn(true);
        TeamDto notValidTeamDto = new TeamDto(duplicatedName);

        //action - checking
        assertThrows(DuplicateException.class, () -> teamService.create(notValidTeamDto));
    }

    @Test
    void update_validIdAndValidTeamDto_shouldReturnTeamDto() {
        //preparation
        Long validId = 1L;
        String validName = "g1";
        Team teamToSave = new Team(validId, validName);
        Team savedTeam = new Team(validId, validName);
        when(teamRepository.existsById(validId)).thenReturn(true);
        when(teamRepository.existsByName(validName)).thenReturn(false);
        when(teamRepository.save(teamToSave)).thenReturn(savedTeam);
        TeamDto validTeamDto = new TeamDto(validName);
        TeamDto expectedTeamDto = new TeamDto(validId, validName);

        //action
        TeamDto updatedTeamDto = teamService.update(validId, validTeamDto);

        //checking
        verify(teamRepository, times(1)).existsByName(validName);
        verify(teamRepository, times(1)).save(teamToSave);
        assertThat(updatedTeamDto).isEqualTo(expectedTeamDto);
    }

    @Test
    void update_validIdAndValidTeamDtoWithWrongFormat_shouldReturnTeamDtoWithRightFormat() {
        //preparation
        Long validId = 1L;
        String validWrongFormattedName = "G1";
        String validRightFormattedName = "g1";
        Team teamToSave = new Team(validId, validRightFormattedName);
        Team savedTeam = new Team(validId, validRightFormattedName);
        when(teamRepository.existsById(validId)).thenReturn(true);
        when(teamRepository.existsByName(validRightFormattedName)).thenReturn(false);
        when(teamRepository.save(teamToSave)).thenReturn(savedTeam);
        TeamDto validTeamDto = new TeamDto(validWrongFormattedName);
        TeamDto expectedTeamDto = new TeamDto(validId, validRightFormattedName);

        //action
        TeamDto updatedTeamDto = teamService.update(validId, validTeamDto);

        //checking
        verify(teamRepository, times(1)).existsByName(validRightFormattedName);
        verify(teamRepository, times(1)).save(teamToSave);
        assertThat(updatedTeamDto).isEqualTo(expectedTeamDto);
    }

    @Test
    void update_notValidIdAndValidTeamDto_shouldThrowNotFoundException() {
        //preparation
        Long notValidId = 1L;
        when(teamRepository.existsById(notValidId)).thenReturn(false);
        TeamDto validTeamDto = new TeamDto("g1");

        //action - checking
        assertThrows(NotFoundException.class, () -> teamService.update(notValidId, validTeamDto));
    }

    @Test
    void update_validIdAndNotValidTeamDtoWithDuplicatedName_shouldThrowDuplicateException() {
        //preparation
        Long validId = 1L;
        String duplicatedName = "g1";
        when(teamRepository.existsById(validId)).thenReturn(true);
        when(teamRepository.existsByName(duplicatedName)).thenReturn(true);
        TeamDto notValidTeamDto = new TeamDto(duplicatedName);

        //action - checking
        assertThrows(DuplicateException.class, () -> teamService.update(validId, notValidTeamDto));
    }

    @Test
    void deleteById_validId_shouldReturnVoid() {
        //preparation
        Long validId = 1L;
        when(teamRepository.existsById(validId)).thenReturn(true);

        //action
        teamService.deleteById(validId);

        //checking
        verify(teamRepository, times(1)).existsById(validId);
        verify(teamRepository, times(1)).deleteById(validId);
    }

    @Test
    void deleteById_notValidId_shouldThrowNotFoundException() {
        //preparation
        Long notValidId = 1L;
        when(teamRepository.existsById(notValidId)).thenReturn(false);

        //action - checking
        assertThrows(NotFoundException.class, () -> teamService.deleteById(notValidId));
    }
}