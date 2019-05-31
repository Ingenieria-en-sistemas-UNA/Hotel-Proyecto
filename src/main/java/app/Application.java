package app;

import app.entity.Client;
import app.entity.Person;
import app.entity.Role;
import app.entity.User;
import app.localDate.LocalDateFormatter;
import app.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.Formatter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import java.time.LocalDate;
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

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Override
    public void run(String... args) {
        try {
            Person person = new Person();
            person.setId("1");
            person.setLastName("Site");
            person.setName("Admin");
            Client client = new Client();
            client.setAddress("Atlantis");
            client.setCellphone("00000000");
            client.setEmail("admin@gmail.com");
            client.setPerson(person);
            client.setMaxReserve(100);
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

    @Bean
    @Primary
    public Formatter<LocalDate> localDateFormatter() {
        return new LocalDateFormatter();
    }
}
