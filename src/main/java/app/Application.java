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
            Person person = new Person();
            person.setId("1");
            person.setLastName("");
            person.setName("");
            Client client = new Client();
            client.setAddress("");
            client.setCellphone("00000000");
            client.setPerson(person);
            User user = new User();
            user.setUsername("admin");
            user.setPassword("1234");
            user.setClient(client);
            user.setRoles(new ArrayList<>(Arrays.asList(Role.ROLE_ADMIN)));
            userService.signup(user);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
