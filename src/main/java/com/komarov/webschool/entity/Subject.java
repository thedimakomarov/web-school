package com.komarov.webschool.entity;

import com.komarov.webschool.dto.SubjectDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "Subject")
@Table(name = "subjects")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = true)
@Getter
@Setter
@ToString
public class Subject extends AuditEntity<String> implements Serializable {
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
}
