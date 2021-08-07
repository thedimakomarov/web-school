package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Teacher;
import com.komarov.webschool.utility.StringUtility;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class TeacherDto {

    @Null(message = "should be null")
    private Long id;

    @NotNull(message = "should be not null")
    @Pattern(regexp = "(?m)^[^0-9_]{2,}$", message = "should be not empty, have at least 2 characters, not contain numbers or '_'")
    private String firstName;

    @Pattern(regexp = "(?m)^[^0-9_]{2,}$", message = "should be not empty, have at least 2 characters, not contain numbers or '_'")
    private String middleName;

    @NotNull(message = "should be not null")
    @Pattern(regexp = "(?m)^[^0-9_]{2,}$", message = "should be not empty, have at least 2 characters, not contain numbers or '_'")
    private String lastName;

    @Pattern(regexp = "\\d{9}", message = "should have 9 characters")
    private String phoneNumber;

    public static TeacherDto parse(Teacher teacher) {
        return new TeacherDto(
                teacher.getId(),
                StringUtility.makeFirstNotNullCharUpperCase(teacher.getFirstName()),
                StringUtility.makeFirstNotNullCharUpperCase(teacher.getMiddleName()),
                StringUtility.makeFirstNotNullCharUpperCase(teacher.getLastName()),
                teacher.getPhoneNumber()
        );
    }

    public static List<TeacherDto> parse(List<Teacher> teachers) {
        return teachers.stream()
                .map(TeacherDto::parse)
                .toList();
    }
}
