package learn.mastery.ui;

import learn.mastery.data.DataException;
import learn.mastery.domain.GuestService;
import learn.mastery.domain.HostService;
import learn.mastery.domain.ReservationService;
import learn.mastery.domain.Result;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Controller {

    private final GuestService guestService;
    private final HostService hostService;
    private final ReservationService reservationService;
    private final View view;

    public Controller(GuestService guestService, HostService hostService, ReservationService reservationService, View view) {
        this.guestService = guestService;
        this.hostService = hostService;
        this.reservationService = reservationService;
        this.view = view;
    }

    public void run() {
        view.displayHeader("Welcome to Don't Wreck My House");
        try {
            runMenuLoop();
        } catch (DataException ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye.");
    }

    private void runMenuLoop() throws DataException {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW_RESERVATIONS:
                    viewReservations();
                    break;
                case ADD_A_RESERVATION:
                    addReservation();
                    break;
                case EDIT_A_RESERVATION:
                    editReservation();
                    break;
                case DELETE_A_RESERVATION:
                    deleteReservation();
                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }

    public void viewReservations() {
        view.displayHeader(MainMenuOption.VIEW_RESERVATIONS.getMessage());
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByEmail(hostEmail);
        if (host == null) {
            view.displayStatus(false, "Host not found.");
            return;
        }
        List<Reservation> reservations = reservationService.findByHost(host);
        view.displayReservations(reservations);
    }

    public void addReservation() throws DataException {
        view.displayHeader(MainMenuOption.ADD_A_RESERVATION.getMessage());
        String guestEmail = view.getGuestEmail();
        Guest guest = guestService.findByEmail(guestEmail);
        if (guest == null) {
            view.displayStatus(false, "Guest not found.");
            return;
        }
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByEmail(hostEmail);
        if (host == null) {
            view.displayStatus(false, "Host not found.");
            return;
        }
        List<Reservation> reservations = reservationService.findByHost(host);
        view.displayReservations(reservations);
        Reservation reservation = view.makeReservation(host, guest);
        boolean confirm = view.displaySummary(reservation);
        if (!confirm) {
            view.displayHeader("Add Action Aborted");
            return;
        }
        Result<Reservation> result = reservationService.add(reservation);
        view.displayStatus(result.isSuccess(), result.getErrorMessages());
        view.displaySuccessMessage(result.isSuccess(), String.format("Reservation %s created.", reservation.getId()));
    }

    public void editReservation() throws DataException {
        view.displayHeader(MainMenuOption.EDIT_A_RESERVATION.getMessage());
        String guestEmail = view.getGuestEmail();
        Guest guest = guestService.findByEmail(guestEmail);
        if (guest == null) {
            view.displayStatus(false, "Guest not found.");
            return;
        }
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByEmail(hostEmail);
        if (host == null) {
            view.displayStatus(false, "Host not found.");
            return;
        }
        List<Reservation> reservations = reservationService.findByBoth(host,guest);
        Reservation reservation = view.chooseReservations(reservations, false);
        if (reservation == null) {
            return;
        }
        Reservation updatedReservation = view.updateReservation(reservation);
        boolean confirm = view.displaySummary(updatedReservation);
        if (!confirm) {
            view.displayHeader("Edit Action Aborted");
            return;
        }
        Result<Reservation> result = reservationService.update(updatedReservation);
        view.displayStatus(result.isSuccess(), result.getErrorMessages());
        view.displaySuccessMessage(result.isSuccess(), String.format("Reservation %s updated.", updatedReservation.getId()));
    }

    public void deleteReservation() throws DataException {
        view.displayHeader(MainMenuOption.DELETE_A_RESERVATION.getMessage());
        String guestEmail = view.getGuestEmail();
        Guest guest = guestService.findByEmail(guestEmail);
        if (guest == null) {
            view.displayStatus(false, "Guest not found.");
            return;
        }
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByEmail(hostEmail);
        if (host == null) {
            view.displayStatus(false, "Host not found.");
            return;
        }
        List<Reservation> reservations = reservationService.findByBoth(host, guest);
        Reservation reservation = view.chooseReservations(reservations, true);
        if (reservation == null) {
            return;
        }
        Result<Reservation> result = reservationService.delete(reservation);
        view.displayStatus(result.isSuccess(), result.getErrorMessages());
        view.displaySuccessMessage(result.isSuccess(), String.format("Reservation %s deleted.", reservation.getId()));
    }
}
