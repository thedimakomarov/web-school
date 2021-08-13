package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Student;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@ToString
public class InnerStudentDto {
    //TODO add id
    //TODO change logic in services
    //TODO change validations
    //TODO check all toLowerCase()

    @NotNull(message = "should be not null")
    @Pattern(regexp = "(?m)^[^0-9_]{2,}$", message = "should be not empty, have at least 2 characters, not contain numbers or '_'")
    private String firstName;

    @NotNull(message = "should be not null")
    @Pattern(regexp = "(?m)^[^0-9_]{2,}$", message = "should be not empty, have at least 2 characters, not contain numbers or '_'")
    private String lastName;

    public static InnerStudentDto parse(Student student) {
        return new InnerStudentDto(
                student.getFirstName(),
                student.getLastName()
        );
    }

    public static List<InnerStudentDto> parse(List<Student> students) {
        return students.stream()
                .map(InnerStudentDto::parse)
                .toList();
    }

    public InnerStudentDto(String firstName, String lastName) {
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
    }
}
