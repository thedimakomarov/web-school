package com.komarov.webschool.configuration;

import com.komarov.webschool.entity.*;
import com.komarov.webschool.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class SchoolConfiguration {

    @Bean
    public CommandLineRunner commandLineRunner(TeacherRepository teacherRepository,
                                               SubjectRepository subjectRepository,
                                               TeamRepository teamRepository,
                                               StudentRepository studentRepository,
                                               LessonRepository lessonRepository) {
        return args -> {
            Teacher arnold = new Teacher("arnold", "flannagan", "000000000");
            Teacher debbie = new Teacher("debbie",  "adams", "000000000");
            Teacher zhess = new Teacher("zhess", "black", "000000000");

            Subject algebra = new Subject("algebra");
            Subject geography = new Subject("geography");
            Subject english = new Subject("english");

            Team alfa = new Team("alfa");

            Lesson lesson1 = new Lesson("topic1", Instant.now(), alfa, arnold, algebra);
            Lesson lesson2 = new Lesson("topic2", Instant.now(), alfa, debbie, geography);
            Lesson lesson3 = new Lesson("topic3", Instant.now(), alfa, zhess, english);
            lessonRepository.saveAll(List.of(lesson1, lesson2, lesson3));

            Student alex = new Student("alex","fitzgerald","000000000");
            Student kate = new Student("kate","dodson","000000000");
            Student robert = new Student("robert","finch","000000000");
            studentRepository.saveAll(List.of(alex, kate, robert));

            studentRepository.findAll().forEach(student -> studentRepository.updateGroupForStudent(student.getId(), alfa.getName()));
        };
    }

    @Bean
    public AuditorAware<String> auditorProvider() {

        /*
          if you are using spring security, you can get the currently logged username with following code segment.
          SecurityContextHolder.getContext().getAuthentication().getName()
         */
        return () -> Optional.ofNullable("defaultUser");
    }
}
