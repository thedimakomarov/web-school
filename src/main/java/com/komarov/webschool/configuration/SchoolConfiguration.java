package com.komarov.webschool.configuration;

import com.komarov.webschool.entity.Teacher;
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
    public CommandLineRunner commandLineRunner(TeacherRepository teacherRepository) {
        return args -> {
            Teacher flannagan = new Teacher("arnold", "flannagan", "000000000");
            Teacher adams = new Teacher("debbie", "adams", "000000000");
            Teacher black = new Teacher("zhess", "black", "000000000");
            teacherRepository.saveAll(List.of(flannagan, adams, black));
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
