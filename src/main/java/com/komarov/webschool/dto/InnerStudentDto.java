package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InnerStudentDto {
    //TODO change logic in services
    //TODO change validations
    //TODO check all toLowerCase()

    private Long id;

    @Pattern(regexp = "(?m)^[^0-9_]{2,}$", message = "should be not empty, have at least 2 characters, not contain numbers or '_'")
    private String firstName;

    @Pattern(regexp = "(?m)^[^0-9_]{2,}$", message = "should be not empty, have at least 2 characters, not contain numbers or '_'")
    private String lastName;

    @Pattern(regexp = "\\d{9}", message = "should have 9 characters")
    private String mobile;

    public static InnerStudentDto parse(Student student) {
        return new InnerStudentDto(
                student.getFirstName(),
                student.getLastName()
        );
    }

    public InnerStudentDto(String firstName, String lastName) {
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
    }
}
