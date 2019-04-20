package app;

import app.controller.UserController;
import app.entity.Client;
import app.entity.Person;
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

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
@PropertySource("classpath:application.properties")
@SpringBootApplication
public class Application implements CommandLineRunner{

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
    public void run(String... args) throws Exception {
        try {
            Person person = new Person("1","","");
            Client client = new Client("","",person,"00000000");
            User user = new User("admin","1234",client,new ArrayList<>(Arrays.asList(Role.ROLE_ADMIN)));
            userService.signup(user);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
