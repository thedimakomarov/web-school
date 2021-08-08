package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Subject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class SubjectDto {

    @Null(message = "should be null")
    private Long id;

    @NotNull(message = "should be not null")
    @Pattern(regexp = "(?m)^[^0-9_]{2,}$", message = "should be not empty, have at least 2 characters, not contain numbers or '_'")
    private String name;

    public static SubjectDto parse(Subject subject) {
        return new SubjectDto(
                subject.getId(),
                subject.getName()
        );
    }

    public static List<SubjectDto> parse(List<Subject> subjects) {
        return subjects.stream()
                .map(SubjectDto::parse)
                .toList();
    }

    public SubjectDto(Long id, String name) {
        this.id = id;
        this.name = name.toLowerCase();
    }
}
