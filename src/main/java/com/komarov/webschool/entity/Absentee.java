package com.komarov.webschool.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity(name = "Absentee")
//@Table(name = "absentees")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = true)
@Getter
@Setter
public class Absentee extends Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "is_present", nullable = false)
    private Boolean isPresent;

    public Absentee(Boolean isPresent) {
        this.isPresent = isPresent;
    }

    @Override
    public String toString() {
        return "Absentee{" +
                "id=" + id +
                ", isPresent=" + isPresent +
                ", lesson=" + getLesson().getTopic() +
                ", student=" + getStudent() +
                '}';
    }
}
