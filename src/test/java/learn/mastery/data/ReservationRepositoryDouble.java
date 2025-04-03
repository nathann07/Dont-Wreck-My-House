package learn.mastery.data;

import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.time.LocalDate;
import java.util.List;

public class ReservationRepositoryDouble implements ReservationRepository {
    @Override
    public List<Reservation> findByHost(Host host) {
        Guest guest = new Guest();
        guest.setId(1);
        return List.of(
                new Reservation(1,LocalDate.parse("2025-10-16"),LocalDate.parse("2025-10-17"),guest,host),
                new Reservation(2,LocalDate.parse("2025-10-27"),LocalDate.parse("2025-11-01"),guest,host),
                new Reservation(3,LocalDate.parse("2025-12-06"),LocalDate.parse("2025-12-07"),guest,host),
                new Reservation(4,LocalDate.parse("2025-09-08"),LocalDate.parse("2025-09-10"),guest,host),
                new Reservation(5,LocalDate.parse("2026-01-07"),LocalDate.parse("2026-01-09"),guest,host)
        );
    }

    @Override
    public List<Reservation> findByBoth(Host host, Guest guest) {
        guest.setId(1);
        return List.of(
                new Reservation(1,LocalDate.parse("2025-10-16"),LocalDate.parse("2025-10-17"),guest,host),
                new Reservation(2,LocalDate.parse("2025-10-27"),LocalDate.parse("2025-11-01"),guest,host),
                new Reservation(3,LocalDate.parse("2025-12-06"),LocalDate.parse("2025-12-07"),guest,host)
        );
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        reservation.setId(13);
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        return reservation.getId() == 2;
    }

    @Override
    public boolean delete(Reservation reservation) throws DataException {
        return reservation.getId() == 5;
    }
}
