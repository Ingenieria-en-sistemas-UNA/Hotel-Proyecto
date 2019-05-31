package app.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("default")
public class DatabaseConfig {

    private static final  String DEFAULT_URL = "jdbc:mysql://localhost:3306/hotel?useTimezone=true&serverTimezone=UTC";
    private static final  String DEFAULT_USERNAME = "root";
    private static final  String DEFAULT_PASSWORD = "root";

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Bean
    public DataSource dataSource(){
        HikariConfig config = new HikariConfig();
        if(dbUrl==null || dbUrl.isEmpty()){
            this.dbUrl = DEFAULT_URL;
            config.setUsername(DEFAULT_USERNAME);
            config.setPassword(DEFAULT_PASSWORD);
        }
        config.setJdbcUrl(dbUrl);
        return new HikariDataSource(config);
    }
}