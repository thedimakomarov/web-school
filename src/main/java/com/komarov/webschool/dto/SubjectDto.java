package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Subject;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class SubjectDto {
    private Long id;
    private String name;

    public static SubjectDto parse(Subject subject) {
        return new SubjectDto(subject.getId(),
                subject.getName());
    }

    public static List<SubjectDto> parse(List<Subject> subjects) {
        return subjects.stream()
                .map(SubjectDto::parse)
                .toList();
    }
}
