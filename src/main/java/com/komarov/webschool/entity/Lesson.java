package com.komarov.webschool.entity;

import com.komarov.webschool.dto.LessonDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "Lesson")
@Table(name = "lessons")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
public class Lesson implements Serializable {

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

    public Lesson(String topic, Instant date, Team team, Teacher teacher, Subject subject) {
        this.topic = topic;
        this.date = date;
        this.team = team;
        this.teacher = teacher;
        this.subject = subject;
    }

    private Lesson(Long id, String topic, Instant date) {
        this.id = id;
        this.topic = topic;
        this.date = date;
    }

    public static Lesson parse(LessonDto lessonDto) {
        return new Lesson(
                lessonDto.getId(),
                lessonDto.getTopic(),
                lessonDto.getDate());
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
}
