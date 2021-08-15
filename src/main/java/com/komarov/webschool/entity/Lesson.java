package com.komarov.webschool.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "Lesson")
@Table(name = "lessons")
@NoArgsConstructor
@Getter
@Setter
public class Lesson extends AuditEntity<String> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "topic", nullable = false)
    private String topic;

    @Column(name = "date", nullable = false)
    private Instant date;

    @ManyToOne(cascade = {CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = LAZY,
            targetEntity = Team.class)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(cascade = {CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = LAZY,
            targetEntity = Teacher.class)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(cascade = {CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = LAZY,
            targetEntity = Subject.class)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public Lesson(Long id, String topic, Instant date) {
        this.id = id;
        this.topic = topic;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "topic='" + topic +
                ", date=" + date +
                ", team=" + team.getName() +
                ", teacher=" + teacher.getFullName() +
                ", subject=" + subject.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(id, lesson.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
