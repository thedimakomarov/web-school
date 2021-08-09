package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Student;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StudentDto {

    @Null(message = "should be null")
    private Long id;

    @NotNull(message = "should be not null")
    @Pattern(regexp = "(?m)^[^0-9_]{2,}$", message = "should be not empty, have at least 2 characters, not contain numbers or '_'")
    private String firstName;

    @NotNull(message = "should be not null")
    @Pattern(regexp = "(?m)^[^0-9_]{2,}$", message = "should be not empty, have at least 2 characters, not contain numbers or '_'")
    private String lastName;

    @Pattern(regexp = "\\d{9}", message = "should have 9 characters")
    private String mobile;

    private String team;

    public static StudentDto parse(Student student) {
        return new StudentDto(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getMobile(),
                student.getTeam()!=null ? student.getTeam().getName() : null
        );
    }

    public static List<StudentDto> parse(List<Student> students) {
        return students.stream()
                .map(StudentDto::parse)
                .toList();
    }

    private StudentDto(Long id, String firstName, String lastName, String mobile, String team) {
        this.id = id;
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
        this.mobile = mobile;
        this.team = team!=null ? team.toLowerCase() : null;
    }
}
