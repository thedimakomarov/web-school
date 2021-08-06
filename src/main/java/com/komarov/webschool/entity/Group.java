package com.komarov.webschool.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Group")
@Table(name = "groups")
@Getter
@Setter
@ToString(exclude = {"students"})
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
public class Group extends AuditEntity<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "group",
            targetEntity = Student.class)
    List<Student> students = new ArrayList<>();

    public Group(String name) {
        this.name = name;
    }

    public Group(String name, List<Student> students) {
        this(name);
        this.students = students;
    }
}
