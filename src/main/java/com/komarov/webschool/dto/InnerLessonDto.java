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

    private Long id;

    @NotBlank(message = "should be not empty and not null")
    private String topic;

    @Null(message = "should be null")
    private Instant date;

    @NotBlank(message = "should be not empty and not null")
    private String teamName;

    @Null(message = "should be null")
    private InnerTeacherDto teacher;

    @NotBlank(message = "should be not empty and not null")
    private String subjectName;

    public InnerLessonDto(String topic, Instant date, String team, InnerTeacherDto teacher, String subject) {
        this.topic = topic.toLowerCase();
        this.date = date;
        this.teamName = team.toLowerCase();
        this.teacher = teacher;
        this.subjectName = subject.toLowerCase();
    }

    public static InnerLessonDto parse(Lesson lesson) {
        return new InnerLessonDto(
                lesson.getTopic(),
                lesson.getDate(),
                lesson.getTeam().getName(),
                InnerTeacherDto.parse(lesson.getTeacher()),
                lesson.getSubject().getName()
        );
    }
}
