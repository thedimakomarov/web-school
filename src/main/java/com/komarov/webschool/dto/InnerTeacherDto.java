package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InnerTeacherDto {
    //TODO change logic in services
    //TODO change validations
    //TODO check all toLowerCase()

    private Long id;

    @Pattern(regexp = "(?m)^[^0-9_]{2,}$", message = "should be not empty, have at least 2 characters, not contain numbers or '_'")
    private String firstName;

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

    public InnerTeacherDto(String firstName, String lastName) {
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
    }
}
