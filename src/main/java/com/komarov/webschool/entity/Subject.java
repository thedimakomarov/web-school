package com.komarov.webschool.entity;

import com.komarov.webschool.dto.SubjectDto;
import com.komarov.webschool.utility.StringUtility;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Subject")
@Table(name = "subjects")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = true)
@Getter
@Setter
@ToString(exclude = {"lessons"})
public class Subject extends AuditEntity<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "subject",
            targetEntity = Lesson.class)
    private List<Lesson> lessons = new ArrayList<>();

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
                StringUtility.makeNotNullStringLowerCase(subjectDto.getName())
        );
    }

    public static List<Subject> parse(List<SubjectDto> subjectsDto) {
        return subjectsDto.stream()
                .map(Subject::parse)
                .toList();
    }
}
