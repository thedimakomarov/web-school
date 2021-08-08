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

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = LAZY,
            targetEntity = Group.class)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = LAZY,
            targetEntity = Teacher.class)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = LAZY,
            targetEntity = Subject.class)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public Lesson(String topic, Instant date, Group group, Teacher teacher, Subject subject) {
        this.topic = topic;
        this.date = date;
        this.group = group;
        this.teacher = teacher;
        this.subject = subject;
    }

    private Lesson(Long id, String topic, Instant date) {
        this.id = id;
        this.topic = topic;
        this.date = date;
    }

    //    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
//    private List<Mark> marks;

//    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
//    private List<Absentee> absentees;

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
                ", group=" + group.getName() +
                ", teacher=" + teacher.getFullName() +
                ", subject=" + subject.getName() +
                '}';
    }
}
