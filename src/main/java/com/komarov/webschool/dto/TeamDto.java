package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Team;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TeamDto {

    @Null(message = "should be null")
    private Long id;

    @NotBlank(message = "should be not empty and not null")
    private String name;

    public static TeamDto parse(Team team) {
        return new TeamDto(
                team.getId(),
                team.getName()
        );
    }

    public static List<TeamDto> parse(List<Team> teams) {
        return teams.stream()
                .map(TeamDto::parse)
                .toList();
    }

    public TeamDto(Long id, String name) {
        this.id = id;
        this.name = name.toLowerCase();
    }
}
