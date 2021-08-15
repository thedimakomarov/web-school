package com.komarov.webschool.repository;

import com.komarov.webschool.entity.Lesson;
import com.komarov.webschool.entity.Subject;
import com.komarov.webschool.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Optional<Lesson> findByTopicAndTeamAndSubject(String topic, Team team, Subject subject);
}
