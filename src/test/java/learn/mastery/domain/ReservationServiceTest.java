package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepositoryDouble;
import learn.mastery.data.HostRepositoryDouble;
import learn.mastery.data.ReservationRepositoryDouble;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    ReservationService service = new ReservationService(
            new ReservationRepositoryDouble(),
            new GuestRepositoryDouble(),
            new HostRepositoryDouble()
    );
    Host host = new Host("3edda6bc-ab95-49a8-8962-d50b53f84b15",
            "Yearnes",
            "eyearnes0@sfgate.com",
            "(806) 1783815",
            "3 Nova Trail",
            "Amarillo",
            "TX",
            79182,
            new BigDecimal("340"),
            new BigDecimal("425"));

    @BeforeEach
    void setup() {
        service = new ReservationService(
                new ReservationRepositoryDouble(),
                new GuestRepositoryDouble(),
                new HostRepositoryDouble()
        );
    }
    @Test
    void shouldFindByHost() {
        List<Reservation> reservations = service.findByHost(host);
        assertEquals(5, reservations.size());

        Guest guest = new Guest(1,"Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV");
        assertEquals(guest, reservations.get(0).getGuest());
    }

    @Test
    void shouldNotFindByNullHost() {
        List<Reservation> reservations = service.findByHost(null);
        assertNull(reservations);
    }

    @Test
    void shouldFindByBoth() {
        List<Reservation> reservations = service.findByBoth(host, new Guest());
        assertEquals(3, reservations.size());

        Guest guest = new Guest(1,"Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV");
        assertEquals(guest, reservations.get(0).getGuest());
    }

    @Test
    void shouldNotFindByNullBoth() {
        List<Reservation> reservations = service.findByBoth(null, new Guest());
        assertNull(reservations);
        reservations = service.findByBoth(host, null);
        assertNull(reservations);
        reservations = service.findByBoth(null, null);
        assertNull(reservations);
    }

    @Test
    void shouldAdd() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);

        Guest guest = new Guest(1,"Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV");
        reservation.setGuest(guest);

        reservation.setStartDate(LocalDate.parse("2025-05-10"));
        reservation.setEndDate(LocalDate.parse("2025-05-20"));

        Result<Reservation> result = service.add(reservation);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(13, result.getPayload().getId());
    }

    @Test
    void shouldNotAddNullHost() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(null);

        Guest guest = new Guest(1,"Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV");
        reservation.setGuest(guest);

        reservation.setStartDate(LocalDate.parse("2025-05-10"));
        reservation.setEndDate(LocalDate.parse("2025-05-20"));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddNullGuest() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);

        reservation.setGuest(null);

        reservation.setStartDate(LocalDate.parse("2025-05-10"));
        reservation.setEndDate(LocalDate.parse("2025-05-20"));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddNullReservation() throws DataException {
        Result<Reservation> result = service.add(null);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddNullStartDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);

        Guest guest = new Guest(1,"Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV");
        reservation.setGuest(guest);

        reservation.setStartDate(null);
        reservation.setEndDate(LocalDate.parse("2025-05-20"));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddNullEndDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);

        Guest guest = new Guest(1,"Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV");
        reservation.setGuest(guest);

        reservation.setStartDate(LocalDate.parse("2025-05-10"));
        reservation.setEndDate(null);

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddInvalidStartDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);

        Guest guest = new Guest(1,"Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV");
        reservation.setGuest(guest);

        reservation.setStartDate(LocalDate.parse("2022-05-10"));
        reservation.setEndDate(LocalDate.parse("2025-05-20"));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());

        reservation.setStartDate(LocalDate.parse("2025-05-30"));

        result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddOverlappingDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);

        Guest guest = new Guest(1,"Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV");
        reservation.setGuest(guest);

        reservation.setStartDate(LocalDate.parse("2025-10-28"));
        reservation.setEndDate(LocalDate.parse("2025-11-02"));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

    @Test
    void shouldUpdate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setHost(host);

        Guest guest = new Guest(1,"Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV");
        reservation.setGuest(guest);

        reservation.setStartDate(LocalDate.parse("2025-05-10"));
        reservation.setEndDate(LocalDate.parse("2025-05-20"));

        Result<Reservation> result = service.update(reservation);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateNonExistingReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(9999);
        reservation.setHost(host);

        Guest guest = new Guest(1,"Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV");
        reservation.setGuest(guest);

        reservation.setStartDate(LocalDate.parse("2025-05-10"));
        reservation.setEndDate(LocalDate.parse("2025-05-20"));

        Result<Reservation> result = service.update(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotUpdateNullHost() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setHost(null);

        Guest guest = new Guest(1,"Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV");
        reservation.setGuest(guest);

        reservation.setStartDate(LocalDate.parse("2025-05-10"));
        reservation.setEndDate(LocalDate.parse("2025-05-20"));

        Result<Reservation> result = service.update(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotUpdateNullGuest() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setHost(host);

        reservation.setGuest(null);

        reservation.setStartDate(LocalDate.parse("2025-05-10"));
        reservation.setEndDate(LocalDate.parse("2025-05-20"));

        Result<Reservation> result = service.update(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotUpdateNullReservation() throws DataException {
        Result<Reservation> result = service.update(null);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotUpdateNullStartDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setHost(host);

        Guest guest = new Guest(1,"Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV");
        reservation.setGuest(guest);

        reservation.setStartDate(null);
        reservation.setEndDate(LocalDate.parse("2025-05-20"));

        Result<Reservation> result = service.update(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotUpdateNullEndDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setHost(host);

        Guest guest = new Guest(1,"Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV");
        reservation.setGuest(guest);

        reservation.setStartDate(LocalDate.parse("2025-05-10"));
        reservation.setEndDate(null);

        Result<Reservation> result = service.update(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotUpdateInvalidStartDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setHost(host);

        Guest guest = new Guest(1,"Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV");
        reservation.setGuest(guest);

        reservation.setStartDate(LocalDate.parse("2022-05-10"));
        reservation.setEndDate(LocalDate.parse("2025-05-20"));

        Result<Reservation> result = service.update(reservation);
        assertFalse(result.isSuccess());

        reservation.setStartDate(LocalDate.parse("2025-05-30"));

        result = service.update(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotUpdateOverlappingDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setHost(host);

        Guest guest = new Guest(1,"Sullivan","Lomas","slomas0@mediafire.com","(702) 7768761","NV");
        reservation.setGuest(guest);

        reservation.setStartDate(LocalDate.parse("2025-10-16"));
        reservation.setEndDate(LocalDate.parse("2025-10-20"));

        Result<Reservation> result = service.update(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldDelete() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(5);
        reservation.setStartDate(LocalDate.parse("2025-10-16"));
        reservation.setEndDate(LocalDate.parse("2025-11-16"));

        Result<Reservation> result = service.delete(reservation);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDeleteNonExistingReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(9999);
        reservation.setStartDate(LocalDate.parse("2025-10-16"));
        reservation.setEndDate(LocalDate.parse("2025-11-16"));

        Result<Reservation> result = service.delete(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotDeletePastReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setId(5);
        reservation.setStartDate(LocalDate.parse("2020-10-16"));
        reservation.setEndDate(LocalDate.parse("2020-11-16"));

        Result<Reservation> result = service.delete(reservation);
        assertFalse(result.isSuccess());
    }
}