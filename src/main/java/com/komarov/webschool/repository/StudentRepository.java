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
    @Query(value = "UPDATE students SET group_id = null WHERE group_id = :groupId", nativeQuery = true)
    void eliminateAllFromGroup(@Param("groupId") Long groupId);
}
