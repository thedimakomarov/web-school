package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Subject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InnerSubjectDto {

    private Long id;

    @Pattern(regexp = "(?m)^[^0-9_]{2,}$", message = "should be not empty, have at least 2 characters, not contain numbers or '_'")
    private String name;

    public InnerSubjectDto(Long id, String name) {
        this.id = id;
        this.name = name.toLowerCase();
    }

    public static InnerSubjectDto parse(Subject subject) {
        return new InnerSubjectDto(
                subject.getId(),
                subject.getName()
        );
    }
}
