package com.komarov.webschool.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceDto {

    @Null(message = "should be null")
    private Long id;

    @Valid
    @NotNull(message = "should be not null")
    private InnerStudentDto student;

    @Valid
    @NotNull(message = "should be not null")
    private InnerLessonDto lesson;

    @NotNull
    private Boolean isPresent;

    @NotNull
    @Size(max = 100, message = "should be bigger than -1 and smaller than 101")
    private Integer mark;
}
