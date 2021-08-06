package com.komarov.webschool.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Teacher")
@Table(name = "teachers")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString
@Getter
@Setter
public class Teacher extends Person implements Serializable {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Teacher(String firstName, String middleName, String lastName, String phoneNumber) {
        super(firstName, middleName, lastName, phoneNumber);
    }

    public Teacher(String firstName, String lastName, String phoneNumber) {
        super(firstName, null, lastName, phoneNumber);
    }
}
