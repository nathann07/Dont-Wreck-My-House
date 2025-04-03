package learn.mastery.data;

import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {
    static final String SEED_FILE_PATH = "./data/reservations_test/reservations-seed-dbe836ef-358b-40b0-a7bd-b40027764d7e.csv";
    static final String TEST_FILE_PATH = "./data/reservations_test/dbe836ef-358b-40b0-a7bd-b40027764d7e.csv";
    static final String TEST_DIR_PATH = "./data/reservations_test";

    final Host host = new Host();
    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR_PATH);

    @BeforeEach
    void setup() throws IOException {
        host.setId("dbe836ef-358b-40b0-a7bd-b40027764d7e");
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldAddReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2025, 7, 25));
        reservation.setEndDate(LocalDate.of(2025, 8, 25));
        Guest guest = new Guest();
        guest.setId(13);
        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setTotal(BigDecimal.ZERO);

        Reservation expected = new Reservation();
        expected.setId(11);
        expected.setStartDate(LocalDate.of(2025, 7, 25));
        expected.setEndDate(LocalDate.of(2025, 8, 25));
        expected.setGuest(guest);
        expected.setHost(host);
        expected.setTotal(BigDecimal.ZERO);

        Reservation actual = repository.add(reservation);

        assertEquals(expected,actual);
        List<Reservation> reservations = repository.findByHost(host);
        assertEquals(11, reservations.size());
    }

    @Test
    void shouldFindReservationByHost() {
        List<Reservation> reservations = repository.findByHost(host);
        assertEquals(10, reservations.size());
    }

    @Test
    void shouldFindReservationByBoth() {
        Guest guest = new Guest();
        guest.setId(474);
        List<Reservation> reservations = repository.findByBoth(host, guest);
        assertEquals(1, reservations.size());
    }

    @Test
    void shouldUpdateReservation() throws DataException {
        Reservation expected = new Reservation();
        expected.setId(9);
        expected.setStartDate(LocalDate.of(2025, 7, 25));
        expected.setEndDate(LocalDate.of(2025, 8, 25));
        Guest guest = new Guest();
        guest.setId(13);
        expected.setGuest(guest);
        expected.setHost(host);
        expected.setTotal(BigDecimal.ZERO);

        assertTrue(repository.update(expected));

        List<Reservation> reservations = repository.findByBoth(host, guest);
        assertEquals(1, reservations.size());
        Reservation actual = repository.findByBoth(host, guest).get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotUpdateNonExistingReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(9999);
        reservation.setStartDate(LocalDate.of(2025, 7, 25));
        reservation.setEndDate(LocalDate.of(2025, 8, 25));
        Guest guest = new Guest();
        guest.setId(13);
        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setTotal(BigDecimal.ZERO);

        assertFalse(repository.update(reservation));
        List<Reservation> reservations = repository.findByBoth(host, guest);
        assertEquals(0, reservations.size());
    }

    @Test
    void shouldDeleteReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(9);
        reservation.setHost(host);

        assertTrue(repository.delete(reservation));
        List<Reservation> reservations = repository.findByHost(host);
        assertEquals(9, reservations.size());
    }

    @Test
    void shouldNotDeleteNonExistingReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(9999);
        reservation.setHost(host);

        assertFalse(repository.delete(reservation));
        List<Reservation> reservations = repository.findByHost(host);
        assertEquals(10, reservations.size());
    }
}