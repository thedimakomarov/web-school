package com.komarov.webschool.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.Instant;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class LessonDto {

    @Null(message = "should be null")
    private Long id;

    @NotBlank(message = "should be not empty and not null")
    private String topic;

    @NotNull(message = "should be not null")
    private Instant date;

    @Valid
    @NotNull
    private InnerTeamDto team;

    @Valid
    @NotNull(message = "should be not null")
    private InnerTeacherDto teacher;

    @Valid
    @NotNull(message = "should be not null")
    private InnerSubjectDto subject;

    public LessonDto(Long id, String topic, Instant date, InnerTeamDto team, InnerTeacherDto teacher, InnerSubjectDto subject) {
        this.id = id;
        this.topic = topic.toLowerCase();
        this.date = date;
        this.team = team;
        this.teacher = teacher;
        this.subject = subject;
    }
}
