package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Group;
import com.komarov.webschool.entity.Student;
import com.komarov.webschool.utility.StringUtility;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class GroupDto {

    @Null(message = "should be null")
    private Long id;

    @NotBlank(message = "should be not empty and not null")
    private String name;

    @Null(message = "should be null. If you need set students in group, update student with needs group.")
    private List<InnerStudentDto> students;

    public static GroupDto parse(Group group) {
        return new GroupDto(
                group.getId(),
                StringUtility.makeFirstNotNullCharUpperCase(group.getName()),
                group.getStudents().stream()
                        .map(InnerStudentDto::parse)
                        .toList()
        );
    }

    public static List<GroupDto> parse(List<Group> groups) {
        return groups.stream()
                .map(GroupDto::parse)
                .toList();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    static class InnerStudentDto {
        private String firstName;
        private String middleName;
        private String lastName;

        public static InnerStudentDto parse(Student student) {
            if(student == null) {
                return new InnerStudentDto();
            }
            return new InnerStudentDto(
                    StringUtility.makeFirstNotNullCharUpperCase(student.getFirstName()),
                    StringUtility.makeFirstNotNullCharUpperCase(student.getMiddleName()),
                    StringUtility.makeFirstNotNullCharUpperCase(student.getLastName())
            );
        }

        public static List<InnerStudentDto> parse(List<Student> students) {
            return students.stream()
                    .map(InnerStudentDto::parse)
                    .toList();
        }
    }
}
