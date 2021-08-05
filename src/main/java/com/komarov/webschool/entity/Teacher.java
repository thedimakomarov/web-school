package com.komarov.webschool.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "Teacher")
@Table(name = "teachers")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString
@Getter
@Setter
public class Teacher extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    public Teacher(String firstName, String middleName, String lastName, String patronymicName, String phoneNumber) {
        super(firstName, middleName, lastName, patronymicName, phoneNumber);
    }
}
