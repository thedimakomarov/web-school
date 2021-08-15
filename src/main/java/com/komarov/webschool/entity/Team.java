package com.komarov.webschool.entity;

import com.komarov.webschool.dto.TeamDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity(name = "Team")
@Table(name = "teams")
@Getter
@Setter
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
