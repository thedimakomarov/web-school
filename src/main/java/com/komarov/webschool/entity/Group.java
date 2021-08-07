package com.komarov.webschool.entity;

import com.komarov.webschool.dto.GroupDto;
import com.komarov.webschool.utility.StringUtility;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Group")
@Table(name = "groups")
@Getter
@Setter
@ToString(exclude = {"students"})
@EqualsAndHashCode(of = {"id"}, callSuper = true)
@NoArgsConstructor
public class Group extends AuditEntity<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "group",
            targetEntity = Student.class)
    List<Student> students = new ArrayList<>();

    public Group(String name) {
        this.name = name;
    }

    public Group(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Group parse(GroupDto groupDto) {
        return new Group(
                groupDto.getId(),
                StringUtility.makeNotNullStringLowerCase(groupDto.getName())
        );
    }

    public static List<Group> parse(List<GroupDto> groupsDto) {
        return groupsDto.stream()
                .map(Group::parse)
                .toList();
    }
}
