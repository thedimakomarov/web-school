package com.komarov.webschool.dto;

import com.komarov.webschool.entity.Group;
import com.komarov.webschool.entity.Student;
import com.komarov.webschool.utility.StringUtility;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class StudentDto {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
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
        private Long id;
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
