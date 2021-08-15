package com.komarov.webschool.entity;

import com.komarov.webschool.dto.SubjectDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity(name = "Subject")
@Table(name = "subjects")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Subject extends AuditEntity<String> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    public Subject(String name) {
        this.name = name;
    }

    private Subject(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Subject parse(SubjectDto subjectDto) {
        return new Subject(
                subjectDto.getId(),
                subjectDto.getName()
        );
    }

    public static List<Subject> parse(List<SubjectDto> subjectsDto) {
        return subjectsDto.stream()
                .map(Subject::parse)
                .toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(id, subject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
