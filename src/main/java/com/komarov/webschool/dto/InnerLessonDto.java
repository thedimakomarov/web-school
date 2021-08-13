package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Lesson;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@ToString
public class InnerLessonDto {
    //TODO add id
    //TODO change logic in services
    //TODO change validations
    //TODO check all toLowerCase()

    @NotBlank(message = "should be not empty and not null")
    private String topic;

    @NotNull(message = "should be not null")
    private Instant date;

    @NotBlank(message = "should be not empty and not null")
    private String team;

    @Valid
    @NotNull(message = "should be not null")
    private InnerTeacherDto teacher;

    @NotNull(message = "should be not null")
    private String subject;

    public static InnerLessonDto parse(Lesson lesson) {
        return new InnerLessonDto(
                lesson.getTopic(),
                lesson.getDate(),
                lesson.getTopic(),
                InnerTeacherDto.parse(lesson.getTeacher()),
                lesson.getSubject().getName()
        );
    }

    public static List<InnerLessonDto> parse(List<Lesson> lessons) {
        return lessons.stream()
                .map(InnerLessonDto::parse)
                .toList();
    }

    public InnerLessonDto(String topic, Instant date, String team, InnerTeacherDto teacher, String subject) {
        this.topic = topic.toLowerCase();
        this.date = date;
        this.team = team.toLowerCase();
        this.teacher = teacher;
        this.subject = subject.toLowerCase();
    }
}
