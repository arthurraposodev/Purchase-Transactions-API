package company.purchases;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * Purchases application startup.
 */
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
@EnableWebFlux
public class PurchasesApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
		SpringApplication.run(PurchasesApplication.class, args);
	}

}
