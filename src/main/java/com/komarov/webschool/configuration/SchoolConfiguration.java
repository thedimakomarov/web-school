package com.komarov.webschool.configuration;

import com.komarov.webschool.entity.Student;
import com.komarov.webschool.entity.Subject;
import com.komarov.webschool.entity.Teacher;
import com.komarov.webschool.entity.Team;
import com.komarov.webschool.repository.StudentRepository;
import com.komarov.webschool.repository.SubjectRepository;
import com.komarov.webschool.repository.TeacherRepository;
import com.komarov.webschool.repository.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class SchoolConfiguration {

    @Bean
    public CommandLineRunner commandLineRunner(TeacherRepository teacherRepository,
                                               SubjectRepository subjectRepository,
                                               TeamRepository teamRepository,
                                               StudentRepository studentRepository/*,
                                               LessonRepository lessonRepository*/) {
        return args -> {
            Teacher arnold = new Teacher("arnold", "flannagan", "000000000");
            Teacher debbie = new Teacher("debbie",  "adams", "000000000");
            Teacher zhess = new Teacher("zhess", "black", "000000000");
            teacherRepository.saveAll(List.of(arnold, debbie, zhess));

            Subject algebra = new Subject("algebra");
            Subject geography = new Subject("geography");
            Subject english = new Subject("english");
            subjectRepository.saveAll(List.of(algebra, geography, english));

            Team alfa = new Team("alfa");

            Student alex = new Student("alex","fitzgerald","000000000");
            alex.setTeam(alfa);
            Student kate = new Student("kate","dodson","000000000");
            kate.setTeam(alfa);
            Student robert = new Student("robert","finch","000000000");
            robert.setTeam(alfa);
            studentRepository.saveAll(List.of(alex, kate, robert));
//            studentRepository.saveAll(List.of(alex, kate, robert));

//            Teacher teacher1 = teacherRepository.getById(1L);
//            Teacher teacher2 = teacherRepository.getById(2L);
//            Teacher teacher3 = teacherRepository.getById(3L);
//
//            Lesson lesson1 = new Lesson("topic1", Instant.now(), "alfa", "algebra", teacher1);
//            Lesson lesson2 = new Lesson("topic2", Instant.now(), "alfa", "geography", teacher2);
//            Lesson lesson3 = new Lesson("topic3", Instant.now(), "alfa", "english", teacher3);
//            lessonRepository.saveAll(List.of(lesson1, lesson2, lesson3));
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
