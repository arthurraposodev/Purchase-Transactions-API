package company.purchases.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration Spring class to set up a global Modelmapper for the domain mapper implementations.
 */
@Configuration
public class ModelMapperConfig {
    /**
     * Global mapper model mapper.
     *
     * @return the model mapper
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
