package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Subject;
import com.komarov.webschool.utility.StringUtility;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class SubjectDto {

    @Null(message = "should be null")
    private Long id;

    @NotNull(message = "should be not null")
    @Pattern(regexp = "(?m)^[^0-9_]{2,}$", message = "should be not empty, have at least 2 characters, not contain numbers or '_'")
    private String name;

    public static SubjectDto parse(Subject subject) {
        return new SubjectDto(
                subject.getId(),
                StringUtility.makeFirstNotNullCharUpperCase(subject.getName())
        );
    }

    public static List<SubjectDto> parse(List<Subject> subjects) {
        return subjects.stream()
                .map(SubjectDto::parse)
                .toList();
    }

}
