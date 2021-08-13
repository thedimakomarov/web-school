package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Performance;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class PerformanceDto {

    @Null(message = "should be null")
    private Long id;

    @Valid
    @NotNull(message = "should be not null")
    private InnerStudentDto innerStudentDto;

    @Valid
    @NotNull(message = "should be not null")
    private InnerLessonDto innerLessonDto;

    @Size(min = 0, max = 100, message = "should be bigger than -1 and smaller than 101")
    private Integer mark;

    @NotNull
    private Boolean isPresent;

    public static PerformanceDto parse(Performance performance) {
        return new PerformanceDto(
                performance.getId(),
                InnerStudentDto.parse(performance.getStudent()),
                InnerLessonDto.parse(performance.getLesson()),
                performance.getMark(),
                performance.getIsPresent()
        );
    }

    public static List<PerformanceDto> parse(List<Performance> performances) {
        return performances.stream()
                .map(PerformanceDto::parse)
                .toList();
    }
}
