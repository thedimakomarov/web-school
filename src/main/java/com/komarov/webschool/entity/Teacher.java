package com.komarov.webschool.entity;

import com.komarov.webschool.dto.TeacherDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "Teacher")
@Table(name = "teachers")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString
@Getter
@Setter
public class Teacher extends Person implements Serializable {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Teacher(String firstName, String middleName, String lastName, String phoneNumber) {
        super(firstName, middleName, lastName, phoneNumber);
    }

    public Teacher(String firstName, String lastName, String phoneNumber) {
        super(firstName, null, lastName, phoneNumber);
    }

    private Teacher(Long id, String firstName, String middleName, String lastName, String phoneNumber) {
        super(firstName, middleName, lastName, phoneNumber);
        this.id = id;
    }

    public static Teacher parse(TeacherDto teacherDto) {
        return new Teacher(teacherDto.getId(),
                teacherDto.getFirstName(),
                teacherDto.getMiddleName(),
                teacherDto.getLastName(),
                teacherDto.getPhoneNumber());
    }

    public static List<Teacher> parse(List<TeacherDto> teacherDto) {
        return teacherDto.stream()
                .map(Teacher::parse)
                .toList();
    }
}
