package app;

import app.entity.Role;
import app.entity.User;
import app.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
@PropertySource("classpath:application.properties")
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... params) {
        try {
            User user = userService.search("admin");
            if (user == null) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("1234");
                admin.setEmail("admin@gmail.com");
                admin.setRoles(new ArrayList<>(Arrays.asList(Role.ROLE_ADMIN)));
                userService.signup(admin);
            }
        } catch (DataIntegrityViolationException e) {
            System.err.println("Ya existe usuario, exepcion esperada");
        }
    }
}
