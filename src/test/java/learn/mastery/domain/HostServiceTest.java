package learn.mastery.domain;

import learn.mastery.data.GuestRepositoryDouble;
import learn.mastery.data.HostRepositoryDouble;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {
    HostService service = new HostService(new HostRepositoryDouble());

    @BeforeEach
    void setup() {
        service = new HostService(new HostRepositoryDouble());
    }

    @Test
    void shouldFindByEmail() {
        Host host = service.findByEmail("eyearnes0@sfgate.com");
        assertNotNull(host);
    }

    @Test
    void shouldNotFindByNullEmail() {
        Host host = service.findByEmail(null);
        assertNull(host);
    }

    @Test
    void shouldNotFindByNonExistingEmail() {
        Host host = service.findByEmail("eyearmlegs0@sfgate.com");
        assertNull(host);
    }
}