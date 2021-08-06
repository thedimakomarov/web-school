package com.komarov.webschool.configuration;

import com.komarov.webschool.entity.Group;
import com.komarov.webschool.entity.Student;
import com.komarov.webschool.entity.Subject;
import com.komarov.webschool.entity.Teacher;
import com.komarov.webschool.repository.GroupRepository;
import com.komarov.webschool.repository.SubjectRepository;
import com.komarov.webschool.repository.TeacherRepository;
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
    public CommandLineRunner commandLineRunner(TeacherRepository teacherRepository, SubjectRepository subjectRepository, GroupRepository groupRepository) {
        return args -> {
            Teacher arnold = new Teacher("arnold", "flannagan", "000000000");
            Teacher debbie = new Teacher("debbie",  "adams", "000000000");
            Teacher zhess = new Teacher("zhess", "black", "000000000");
            teacherRepository.saveAll(List.of(arnold, debbie, zhess));

            Subject algebra = new Subject("Algebra");
            Subject geography = new Subject("Geography");
            Subject english = new Subject("English");
            subjectRepository.saveAll(List.of(algebra, geography, english));

            Group alfa = new Group("Alfa");

            Student alex = new Student("alex","fitzgerald","000000000");
            alex.setGroup(alfa);
            Student kate = new Student("kate","dodson","000000000");
            kate.setGroup(alfa);
            Student robert = new Student("robert","finch","000000000");
            robert.setGroup(alfa);

            alfa.setStudents(List.of(alex, kate, robert));
            groupRepository.save(alfa);
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
