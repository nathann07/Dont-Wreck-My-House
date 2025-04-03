package learn.mastery.data;

import learn.mastery.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {
    static final String SEED_PATH = "./data/guests-seed.csv";
    static final String TEST_PATH = "./data/guests-test.csv";

    GuestFileRepository repository = new GuestFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindByEmail() {
        Guest expected = new Guest(10,
                "Isabel",
                "Ganter",
                "iganter9@privacy.gov.au",
                "(915) 5895326",
                "TX");
        Guest actual = repository.findByEmail("iganter9@privacy.gov.au");
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindById() {
        Guest expected = new Guest(10,
                "Isabel",
                "Ganter",
                "iganter9@privacy.gov.au",
                "(915) 5895326",
                "TX");
        Guest actual = repository.findById(10);
        assertEquals(expected, actual);
    }
}