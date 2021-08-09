package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Lesson;
import com.komarov.webschool.entity.Teacher;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
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

    @NotNull(message = "should be not null")
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

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    public static class InnerTeacherDto {
        @NotNull(message = "should be not null")
        @Pattern(regexp = "(?m)^[^0-9_]{2,}$", message = "should be not empty, have at least 2 characters, not contain numbers or '_'")
        private String firstName;

        @NotNull(message = "should be not null")
        @Pattern(regexp = "(?m)^[^0-9_]{2,}$", message = "should be not empty, have at least 2 characters, not contain numbers or '_'")
        private String lastName;

        public static InnerTeacherDto parse(Teacher teacher) {
            return new InnerTeacherDto(
                    teacher.getFirstName(),
                    teacher.getLastName()
            );
        }

        public static List<InnerTeacherDto> parse(List<Teacher> teachers) {
            return teachers.stream()
                    .map(InnerTeacherDto::parse)
                    .toList();
        }
    }
}
