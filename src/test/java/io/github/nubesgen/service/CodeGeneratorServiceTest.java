package io.github.nubesgen.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Generated;
import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CodeGeneratorServiceTest {

    private final CodeGeneratorService codeGeneratorService;

    @Autowired
    public CodeGeneratorServiceTest(CodeGeneratorService codeGeneratorService) {
        this.codeGeneratorService = codeGeneratorService;
    }

    private static CodeGeneratorProperties properties = new CodeGeneratorProperties();

    @BeforeAll
    public static void init() {
        properties.setResourceGroup("nubesgen");
        properties.setApplicationName("sampleNubesApplication");
        properties.setLocation("westeurope");
    }

    @Test
    void generateAzureConfiguration() {
        Map<String, String> configuration = this.codeGeneratorService.generateAzureConfiguration(properties);
        assertEquals(3, configuration.size());
    }

    @Test
    void generateFile() throws IOException {
        String result = this.codeGeneratorService.generateFile("azure/dev/variables.tf", properties);
        File testFile = new ClassPathResource("nubesgen/azure/dev/variables.tf").getFile();
        String test = new String(
                Files.readAllBytes(testFile.toPath()));

        assertEquals(test, result);
    }
}
