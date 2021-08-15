package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Lesson;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.Instant;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class LessonDto {

    @Null(message = "should be null")
    private Long id;

    @NotBlank(message = "should be not empty and not null")
    private String topic;

    @NotNull(message = "should be not null")
    private Instant date;

    @NotBlank(message = "should be not empty and not null")
    private String team;

    @Valid
    @NotNull(message = "should be not null")
    private InnerTeacherDto teacher;

    @NotBlank(message = "should be not empty and not null")
    private String subject;

    public static LessonDto parse(Lesson lesson) {
        return new LessonDto(
                lesson.getId(),
                lesson.getTopic(),
                lesson.getDate(),
                lesson.getTeam().getName(),
                InnerTeacherDto.parse(lesson.getTeacher()),
                lesson.getSubject().getName()
        );
    }

    public static List<LessonDto> parse(List<Lesson> lessons) {
        return lessons.stream()
                .map(LessonDto::parse)
                .toList();
    }

    private LessonDto(Long id, String topic, Instant date, String team, InnerTeacherDto teacher, String subject) {
        this.id = id;
        this.topic = topic.toLowerCase();
        this.date = date;
        this.team = team.toLowerCase();
        this.teacher = teacher;
        this.subject = subject.toLowerCase();
    }
}
