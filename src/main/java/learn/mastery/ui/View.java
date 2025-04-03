package learn.mastery.ui;

import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class View {

    private final ConsoleIO io;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public View(ConsoleIO io) {
        this.io = io;
    }

    public MainMenuOption selectMainMenuOption() {
        displayHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenuOption option : MainMenuOption.values()) {
            io.printf("%s. %s%n", option.getValue(), option.getMessage());

            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }

    public Reservation chooseReservations(List<Reservation> reservations, boolean onlyFutureReservations) {

        // if onlyFutureReservations is true, only print future reservations
        if (onlyFutureReservations) {
            reservations = reservations.stream()
                    .filter(i -> i.getEndDate().isAfter(LocalDate.now()))
                    .collect(Collectors.toList());

            if (reservations.isEmpty()) {
                io.println("No upcoming reservations found for this host.");
                return null;
            }
        }

        displayReservations(reservations);

        if (reservations.isEmpty()) {
            return null;
        }

        int reservationId = io.readInt("Reservation ID: ");
        Reservation reservation = reservations.stream()
                .filter(i -> i.getId() == reservationId)
                .findFirst()
                .orElse(null);

        if (reservation == null) {
            displayStatus(false, String.format("No reservation with id %s found.", reservationId));
        }

        return reservation;
    }

    public Reservation makeReservation(Host host, Guest guest) {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStartDate(io.readLocalDate("Start (MM/dd/yyyy): "));
        reservation.setEndDate(io.readLocalDate("End (MM/dd/yyyy): "));
        reservation.setTotal(reservation.calculateTotal());
        return reservation;
    }

    public Reservation updateReservation(Reservation reservation) {
        Reservation updatedReservation = new Reservation();
        updatedReservation.setId(reservation.getId());
        updatedReservation.setHost(reservation.getHost());
        updatedReservation.setGuest(reservation.getGuest());
        System.out.println("Press [Enter] to keep original value.");
        updatedReservation.setStartDate(io.readOptionalLocalDate(
                String.format("Start (%s/%s/%s): ",
                        reservation.getStartDate().getMonthValue(),
                        reservation.getStartDate().getDayOfMonth(),
                        reservation.getStartDate().getYear()),
                reservation.getStartDate())
        );
        updatedReservation.setEndDate(io.readOptionalLocalDate(
                String.format("End (%s/%s/%s): ",
                        reservation.getEndDate().getMonthValue(),
                        reservation.getEndDate().getDayOfMonth(),
                        reservation.getEndDate().getYear()),
                reservation.getEndDate())
        );
        updatedReservation.setTotal(updatedReservation.calculateTotal());
        return updatedReservation;
    }

    public String getHostEmail() {
        return io.readRequiredString("Host email: ");
    }

    public String getGuestEmail() {
        return io.readRequiredString("Guest email: ");
    }

    public void displayReservations(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            io.println("No reservations found for that host.");
            return;
        }

        // sort reservations by start date
        reservations.sort(Comparator.comparing(Reservation::getStartDate));

        Host host = reservations.get(0).getHost();
        displayHeader(String.format("%s: %s, %s", host.getLastName(), host.getCity(), host.getState()));

        for (Reservation reservation : reservations) {
            io.printf("ID: %s, %s - %s, Guest: %s, %s, Email: %s%n",
                    reservation.getId(),
                    reservation.getStartDate().format(formatter),
                    reservation.getEndDate().format(formatter),
                    reservation.getGuest().getLastName(),
                    reservation.getGuest().getFirstName(),
                    reservation.getGuest().getEmail()
            );
        }
    }

    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
}

    public void displayException(Exception ex) {
        displayHeader("A critical error occurred:");
        io.println(ex.getMessage());
    }

    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }

    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }
    }

    public void displaySuccessMessage(boolean success, String message) {
        if (success) {
            io.println(message);
        }
    }

    public boolean displaySummary(Reservation reservation) {
        displayHeader("Summary");
        io.printf("Start: %s%n", reservation.getStartDate().format(formatter));
        io.printf("End: %s%n", reservation.getEndDate().format(formatter));
        io.printf("Total: $%.2f%n", reservation.getTotal());
        return io.readBoolean("Is this okay? [y/n]: ");
    }
}
