package com.komarov.webschool.configuration;

import com.komarov.webschool.entity.Subject;
import com.komarov.webschool.entity.Teacher;
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
    public CommandLineRunner commandLineRunner(TeacherRepository teacherRepository, SubjectRepository subjectRepository) {
        return args -> {
            Teacher timur = new Teacher("timur", "demin", "damirovich", "000000000");
            Teacher alexander = new Teacher("alexander", "rubtsov", "yurievich", "000000000");
            Teacher safiya = new Teacher("safiya", "mikhailova", "nikitichna", "000000000");
            teacherRepository.saveAll(List.of(timur, alexander, safiya));

            Subject algebra = new Subject("Algebra");
            Subject geography = new Subject("Geography");
            Subject english = new Subject("English");
            subjectRepository.saveAll(List.of(algebra, geography, english));
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
