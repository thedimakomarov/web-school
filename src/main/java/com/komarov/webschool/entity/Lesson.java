package com.komarov.webschool.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

//@Entity(name = "Lesson")
//@Table(name = "lessons")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "topic", nullable = false)
    private String topic;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<Mark> marks;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<Absentee> absentees;

    @Override
    public String toString() {
        return "Lesson{" +
                "topic='" + topic + '\'' +
                ", group=" + group +
                ", teacher=" + teacher +
                ", subject=" + subject +
                '}';
    }
}
