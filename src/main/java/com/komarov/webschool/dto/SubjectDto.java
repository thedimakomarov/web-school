package com.komarov.webschool.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SubjectDto {

    @Null(message = "should be null")
    private Long id;

    @NotNull(message = "should be not null")
    @Pattern(regexp = "(?m)^[^0-9_]{2,}$", message = "should be not empty, have at least 2 characters, not contain numbers or '_'")
    private String name;

    public SubjectDto(Long id, String name) {
        this.id = id;
        this.name = name.toLowerCase();
    }

    public SubjectDto(String name) {
        this.name = name.toLowerCase();
    }
}
