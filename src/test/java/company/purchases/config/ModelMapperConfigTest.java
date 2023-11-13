package company.purchases.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ModelMapperConfigTest {

    @Test
    @DisplayName("ModelMapper bean should not be null")
    void modelMapperShouldNotBeNullTest() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
        ModelMapper modelMapper = modelMapperConfig.modelMapper();
        assertNotNull(modelMapper, "ModelMapper bean should not be null");
    }
}