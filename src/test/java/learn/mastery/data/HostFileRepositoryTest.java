package learn.mastery.data;

import learn.mastery.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HostFileRepositoryTest {
    static final String SEED_PATH = "./data/hosts-seed.csv";
    static final String TEST_PATH = "./data/hosts-test.csv";

    HostFileRepository repository = new HostFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindByEmail() {
        Host expected = new Host("d63304e3-de36-4ecc-8f8f-847431ffff64",
                "Zuppa",
                "ezuppa8@yale.edu",
                "(915) 4423313",
                "304 Aberg Trail",
                "El Paso",
                "TX",
                79905,
                new BigDecimal("399"),
                new BigDecimal("498.75"));
        Host actual = repository.findByEmail("ezuppa8@yale.edu");
        assertEquals(expected, actual);
    }
}