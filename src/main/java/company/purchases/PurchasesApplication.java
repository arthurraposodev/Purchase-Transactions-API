package company.purchases;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
@EnableWebFlux
public class PurchasesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PurchasesApplication.class, args);
	}

}
