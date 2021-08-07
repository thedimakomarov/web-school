package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Teacher;
import com.komarov.webschool.utility.StringUtility;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class TeacherDto {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
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
