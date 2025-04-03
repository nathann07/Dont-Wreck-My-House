package learn.mastery.domain;

import learn.mastery.data.GuestRepositoryDouble;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {
    GuestService service = new GuestService(new GuestRepositoryDouble());

    @BeforeEach
    void setup() {
        service = new GuestService(new GuestRepositoryDouble());
    }

    @Test
    void shouldFindByEmail() {
        Guest guest = service.findByEmail("ogecks1@dagondesign.com");
        assertNotNull(guest);

    }

    @Test
    void shouldNotFindByNullEmail() {
        Guest guest = service.findByEmail(null);
        assertNull(guest);
    }

    @Test
    void shouldNotFindByNonExistingEmail() {
        Guest guest = service.findByEmail("geckos1@dragondesign.com");
        assertNull(guest);
    }
}