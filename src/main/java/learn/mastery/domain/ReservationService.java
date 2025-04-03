package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepository;
import learn.mastery.data.HostRepository;
import learn.mastery.data.ReservationRepository;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final HostRepository hostRepository;

    public ReservationService(ReservationRepository reservationRepository, GuestRepository guestRepository, HostRepository hostRepository) {
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
    }

    public List<Reservation> findByHost(Host host) {
        if (host == null) {  // host not found
            return null;
        }

        // populate reservation guest fields with the actual guest
        List<Reservation> reservations = reservationRepository.findByHost(host);
        reservations.forEach(i -> i.setGuest(guestRepository.findById(i.getGuest().getId())));
        return reservations;
    }

    public List<Reservation> findByBoth(Host host, Guest guest) {
        if (host == null || guest == null) {  // host or guest not found
            return null;
        }

        // populate reservation guest fields with the actual guest
        List<Reservation> reservations = reservationRepository.findByBoth(host, guest);
        reservations.forEach(i -> i.setGuest(guestRepository.findById(i.getGuest().getId())));
        return reservations;
    }

    public Result<Reservation> add(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        boolean timeOverlap = reservationRepository.findByHost(reservation.getHost()).stream()  // check all reservations for overlapping time
                .anyMatch(i -> i.getStartDate().isBefore(reservation.getEndDate()) &&
                        i.getEndDate().isAfter(reservation.getStartDate()));
        if (timeOverlap) {
            result.addErrorMessage("Reservation cannot overlap time with an existing reservation.");
            return result;
        }

        result.setPayload(reservationRepository.add(reservation));
        return result;
    }

    public Result<Reservation> update(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        boolean timeOverlap = reservationRepository.findByHost(reservation.getHost()).stream()  // check all reservations for overlapping time
                .anyMatch(i ->
                        i.getStartDate().isBefore(reservation.getEndDate()) &&
                        i.getEndDate().isAfter(reservation.getStartDate()) &&
                        i.getId() != reservation.getId());  // EXCEPT for the reservation we are updating, it's fine if it overlaps that
        if (timeOverlap) {
            result.addErrorMessage("Reservation cannot overlap time with an existing reservation.");
            return result;
        }

        boolean updated = reservationRepository.update(reservation);  // if this fails it's because the id we are trying to update doesn't exist

        if (!updated) {
            result.addErrorMessage("That reservation does not exist.");  // if id doesn't exist, add error message to fail the update action
        }
        return result;
    }

    public Result<Reservation> delete(Reservation reservation) throws DataException {
        Result<Reservation> result = new Result<>();

        if (reservation.getStartDate().isBefore(LocalDate.now())) {
            result.addErrorMessage("Cannot cancel a reservation that's in the past.");
            return result;
        }

        boolean deleted = reservationRepository.delete(reservation);  // if this fails it's because the id we are trying to delete doesn't exist

        if (!deleted) {
            result.addErrorMessage("That reservation does not exist.");  // if id doesn't exist, add error message to fail the delete action
        }
        return result;
    }

    private Result<Reservation> validate(Reservation reservation) {
        Result<Reservation> result = new Result<>();
        // validate nulls
        if (reservation == null) {
            result.addErrorMessage("Reservation is required.");
            return result;
        }
        if (reservation.getHost() == null) {
            result.addErrorMessage("Host is required.");
        }
        if (reservation.getGuest() == null) {
            result.addErrorMessage("Guest is required.");
        }
        if (reservation.getStartDate() == null) {
            result.addErrorMessage("Start date is required.");
        }
        if (reservation.getEndDate() == null) {
            result.addErrorMessage("End date is required.");
        }
        if (!result.isSuccess()) {
            return result;
        }

        // validate children
        Host host = reservation.getHost();
        if (host.getId() == null || hostRepository.findByEmail(host.getEmail()) == null) {
            result.addErrorMessage("Host does not exist.");
        }
        Guest guest = reservation.getGuest();
        if (guestRepository.findByEmail(guest.getEmail()) == null) {
            result.addErrorMessage("Guest does not exist.");
        }

        // validate dates
        if (reservation.getStartDate().isAfter(reservation.getEndDate())) {
            result.addErrorMessage("Start date must be before end date.");
        }
        if (reservation.getStartDate().isBefore(LocalDate.now())) {
            result.addErrorMessage("Start date must be in the future.");
        }

        return result;
    }
}
