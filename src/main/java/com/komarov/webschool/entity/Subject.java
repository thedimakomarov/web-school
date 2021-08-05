package com.komarov.webschool.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Subject")
@Table(name = "subjects")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString
@Getter
@Setter
public class Subject extends AuditEntity<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    public Subject(String name) {
        this.name = name;
    }
}
