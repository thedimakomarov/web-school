package com.komarov.webschool.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity(name = "Mark")
//@Table(name = "marks")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
public class Mark extends Note{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "mark", nullable = false)
    private Integer mark;

    public Mark(Integer mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "id=" + id +
                ", mark=" + mark +
                ", lesson=" + getLesson().getTopic() +
                ", student=" + getStudent() +
                '}';
    }
}
