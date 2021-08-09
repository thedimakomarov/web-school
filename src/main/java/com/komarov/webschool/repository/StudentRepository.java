package com.komarov.webschool.repository;

import com.komarov.webschool.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE students SET team_id = null WHERE team_id = :team_id", nativeQuery = true)
    void eliminateAllFromTeam(@Param("team_id") Long teamId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE students SET team_id = " +
            "(SELECT id FROM teams WHERE name = :team_name) " +
            "WHERE id = :id",
            nativeQuery = true)
    void updateGroupForStudent(@Param("id") Long id, @Param("team_name") String teamName);
}
