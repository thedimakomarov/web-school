package com.komarov.webschool.entity;

import com.komarov.webschool.dto.StudentDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "Student")
@Table(name = "students")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = true)//TODO: avoid this annotation
@Getter
@Setter
public class Student extends Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = LAZY,
            targetEntity = Team.class)
    @JoinColumn(name = "team_id")
    private Team team;

    private Student(Long id, String firstName, String lastName, String mobile) {
        super(firstName, lastName, mobile);
        this.id = id;
    }

    public Student(String firstName, String lastName, String mobile) {
        super(firstName, lastName, mobile);
    }

    public static Student parse(StudentDto studentDto) {
        return new Student(
                studentDto.getId(),
                studentDto.getFirstName(),
                studentDto.getLastName(),
                studentDto.getMobile()
        );
    }

    public static List<Student> parse(List<StudentDto> studentsDto) {
        return studentsDto.stream()
                .map(Student::parse)
                .toList();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName=" + getFirstName() +
                ", lastName=" + getLastName() +
                ", mobile=" + getMobile() +
                ", team=" + team.getName() +
                '}';
    }
}
