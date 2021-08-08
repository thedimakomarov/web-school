package com.komarov.webschool.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//TODO: finish tish class
//@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public abstract class Note extends AuditEntity<String>{
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
}
