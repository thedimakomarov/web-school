package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Group;
import com.komarov.webschool.entity.Student;
import com.komarov.webschool.utility.StringUtility;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class StudentDto {
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

    @Valid
    private InnerGroupDto group;

    public static StudentDto parse(Student student) {
        return new StudentDto(
                student.getId(),
                StringUtility.makeFirstNotNullCharUpperCase(student.getFirstName()),
                StringUtility.makeFirstNotNullCharUpperCase(student.getMiddleName()),
                StringUtility.makeFirstNotNullCharUpperCase(student.getLastName()),
                student.getPhoneNumber(),
                InnerGroupDto.parse(student.getGroup())
        );
    }

    public static List<StudentDto> parse(List<Student> students) {
        return students.stream()
                .map(StudentDto::parse)
                .toList();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InnerGroupDto {
        @Null(message = "should be null")
        private Long id;

        @NotBlank(message = "should be not empty and not null")
        private String name;

        public static InnerGroupDto parse(Group group) {
            if(group == null) {
                return new InnerGroupDto();
            }
            return new InnerGroupDto(
                    group.getId(),
                    StringUtility.makeFirstNotNullCharUpperCase(group.getName())
            );
        }

        public static List<InnerGroupDto> parse(List<Group> groups) {
            return groups.stream()
                    .map(InnerGroupDto::parse)
                    .toList();
        }
    }
}
