package com.komarov.webschool.configuration;

import com.komarov.webschool.repository.TeacherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.util.Optional;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class SchoolConfiguration {

    private DataSource dataSource;

    public SchoolConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("sql/data-web-school.sql"));
        resourceDatabasePopulator.execute(dataSource);
    }

    @Bean
    public CommandLineRunner commandLineRunner(TeacherRepository teacherRepository) {
        return args -> {
//            Teacher flannagan = new Teacher("arnold", "flannagan", "000000000");
//            Teacher adams = new Teacher("debbie", "adams", "000000000");
//            Teacher black = new Teacher("zhess", "black", "000000000");
//            teacherRepository.saveAll(List.of(flannagan, adams, black));
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
