package com.komarov.webschool.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

//TODO: finish tish class
//@Entity(name = "Mark")
//@Table(name = "marks")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = true)
@Getter
@Setter
public class Mark extends Note implements Serializable {
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
