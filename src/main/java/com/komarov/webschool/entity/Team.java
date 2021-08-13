package com.komarov.webschool.entity;

import com.komarov.webschool.dto.TeamDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity(name = "Team")
@Table(name = "teams")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"}, callSuper = true)//TODO: avoid this annotation
@NoArgsConstructor
public class Team extends AuditEntity<String> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public Team(String name) {
        this.name = name;
    }

    public Team(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Team parse(TeamDto teamDto) {
        return new Team(
                teamDto.getId(),
                teamDto.getName()
        );
    }

    public static List<Team> parse(List<TeamDto> teamsDto) {
        return teamsDto.stream()
                .map(Team::parse)
                .toList();
    }
}
