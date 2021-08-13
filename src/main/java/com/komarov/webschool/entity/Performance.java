package com.komarov.webschool.entity;

import com.komarov.webschool.dto.PerformanceDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "Performance")
@Table(name = "performances")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})//TODO: avoid this annotation
@Getter
@Setter
public class Performance implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = LAZY,
            targetEntity = Student.class)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(cascade = {CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = LAZY,
            targetEntity = Lesson.class)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Column(name = "is_present", nullable = false)
    private Boolean isPresent;

    @Column(name = "mark", nullable = false)
    private Integer mark;

    public Performance(Boolean isPresent, Integer mark) {
        this.isPresent = isPresent;
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "id=" + id +
                ", isPresent=" + isPresent +
                ", mark=" + mark +
                ", student=" + getStudent() +
                ", lesson=" + getLesson().getTopic() +
                '}';
    }

    public static Performance parse(PerformanceDto progressDto) {
        return new Performance(
                progressDto.getIsPresent(),
                progressDto.getMark()
        );
    }

    public static List<Performance> parse(List<PerformanceDto> marksDto) {
        return marksDto.stream()
                .map(Performance::parse)
                .toList();
    }
}