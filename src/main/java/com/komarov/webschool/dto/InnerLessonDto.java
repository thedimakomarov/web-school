package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Lesson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.time.Instant;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InnerLessonDto {
    //TODO change logic in services
    //TODO check all toLowerCase()

    private Long id;

    @NotBlank(message = "should be not empty and not null")
    private String topic;

    @Null(message = "should be null")
    private Instant date;

    @NotBlank(message = "should be not empty and not null")
    private String team;

    @Null(message = "should be null")
    private InnerTeacherDto teacher;

    @NotBlank(message = "should be not empty and not null")
    private String subject;

    public static InnerLessonDto parse(Lesson lesson) {
        return new InnerLessonDto(
                lesson.getTopic(),
                lesson.getDate(),
                lesson.getTeam().getName(),
                InnerTeacherDto.parse(lesson.getTeacher()),
                lesson.getSubject().getName()
        );
    }

    public InnerLessonDto(String topic, Instant date, String team, InnerTeacherDto teacher, String subject) {
        this.topic = topic.toLowerCase();
        this.date = date;
        this.team = team.toLowerCase();
        this.teacher = teacher;
        this.subject = subject.toLowerCase();
    }
}
