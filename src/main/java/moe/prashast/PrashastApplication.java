package moe.prashast;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication //(exclude = {KafkaAutoConfiguration.class, CassandraAutoConfiguration.class})
@EnableTransactionManagement
public class PrashastApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PrashastApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(PrashastApplication.class, args);
	}




}
