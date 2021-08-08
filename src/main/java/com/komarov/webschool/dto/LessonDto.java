package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Lesson;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.Instant;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class LessonDto {

    @Null(message = "should be null")
    private Long id;

    @NotNull(message = "should be not null")
    private String topic;

    @NotNull(message = "should be not null")
    private Instant date;

    @NotNull(message = "should be not null")
    private InnerGroupDto group;

    @NotNull(message = "should be not null")
    private TeacherDto teacherDto;

    @NotNull(message = "should be not null")
    private SubjectDto subjectDto;

    public static LessonDto parse(Lesson lesson) {
        return new LessonDto(
                lesson.getId(),
                lesson.getTopic(),
                lesson.getDate(),
                InnerGroupDto.parse(lesson.getGroup()),
                TeacherDto.parse(lesson.getTeacher()),
                SubjectDto.parse(lesson.getSubject())
        );
    }

    public static List<LessonDto> parse(List<Lesson> lessons) {
        return lessons.stream()
                .map(LessonDto::parse)
                .toList();
    }
}
