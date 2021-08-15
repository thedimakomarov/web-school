package com.komarov.webschool.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "Teacher")
@Table(name = "teachers")
@NoArgsConstructor
@Getter
@Setter
public class Teacher extends Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Teacher(Long id, String firstName, String lastName, String mobile) {
        super(firstName, lastName, mobile);
        this.id = id;
    }

    public String getFullName() {
        return getFirstName() + getLastName();
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", firstName=" + getFirstName() +
                ", lastName=" + getLastName() +
                ", mobile=" + getMobile() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(id, teacher.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
