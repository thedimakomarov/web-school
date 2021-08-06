package com.komarov.webschool.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "Student")
@Table(name = "students")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
public class Student extends Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = LAZY,
            targetEntity = Group.class)
    @JoinColumn(name = "group_id")
    @JsonIgnore
    private Group group;

    public Student(String firstName, String middleName, String lastName, String phoneNumber) {
        super(firstName, middleName, lastName, phoneNumber);
    }

    public Student(String firstName, String lastName, String phoneNumber) {
        super(firstName, null, lastName, phoneNumber);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", group=" + group.getName() +
                '}';
    }
}
