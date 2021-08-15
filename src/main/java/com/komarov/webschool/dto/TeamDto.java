package com.komarov.webschool.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TeamDto {

    @Null(message = "should be null")
    private Long id;

    @NotBlank(message = "should be not empty and not null")
    private String name;

    public TeamDto(Long id, String name) {
        this.id = id;
        this.name = name.toLowerCase();
    }
}
