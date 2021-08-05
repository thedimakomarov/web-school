package com.komarov.webschool.configuration;

import com.komarov.webschool.entity.Teacher;
import com.komarov.webschool.repository.TeacherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SchoolConfiguration {

    @Bean
    public CommandLineRunner commandLineRunner(TeacherRepository teacherRepository) {
        return args -> {
            Teacher deminTimurDamirovich = new Teacher("demin", null, "timur", "damirovich", "000000000");
            Teacher rubtsovAlexanderYurievich = new Teacher("rubtsov", null, "alexander", "yurievich", "000000000");
            Teacher mikhailovaSafiyaNikitichna = new Teacher("mikhailova", null, "safiya", "nikitichna", "000000000");

            teacherRepository.saveAll(List.of(deminTimurDamirovich, rubtsovAlexanderYurievich, mikhailovaSafiyaNikitichna));
        };
    }
}
