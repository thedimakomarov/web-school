package com.komarov.webschool.entity;

import com.komarov.webschool.dto.TeacherDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity(name = "Teacher")
@Table(name = "teachers")
@NoArgsConstructor
@Getter
@Setter
public class Teacher extends Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Teacher(Long id, String firstName, String lastName, String mobile) {
        super(firstName, lastName, mobile);
        this.id = id;
    }

    public Teacher(String firstName, String lastName, String mobile) {
        super(firstName, lastName, mobile);
    }

    public static Teacher parse(TeacherDto teacherDto) {
        return new Teacher(
                teacherDto.getId(),
                teacherDto.getFirstName(),
                teacherDto.getLastName(),
                teacherDto.getMobile()
        );
    }

    public static List<Teacher> parse(List<TeacherDto> teachersDto) {
        return teachersDto.stream()
                .map(Teacher::parse)
                .toList();
    }

    public String getFullName() {
        return getFirstName() + getLastName();
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", firstName=" + getFirstName() +
                ", lastName=" + getLastName() +
                ", mobile=" + getMobile() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(id, teacher.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
