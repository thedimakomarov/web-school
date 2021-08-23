package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InnerTeamDto {

    private Long id;

    @NotBlank(message = "should be not empty and not null")
    private String name;

    public InnerTeamDto(Long id, String name) {
        this.id = id;
        this.name = name.toLowerCase();
    }

    public static InnerTeamDto parse(Team team) {
        return new InnerTeamDto(
                team.getId(),
                team.getName()
        );
    }
}
