package progetto;

import org.springframework.boot.SpringApplication;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@ServletComponentScan
public class ProgettoApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProgettoApplication.class, args);
	}
}
