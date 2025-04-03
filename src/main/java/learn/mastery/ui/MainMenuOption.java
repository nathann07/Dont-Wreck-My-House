package learn.mastery.ui;

public enum MainMenuOption {

    EXIT(0, "Exit"),
    VIEW_RESERVATIONS(1, "View Reservations"),
    ADD_A_RESERVATION(2, "Add a Reservation"),
    EDIT_A_RESERVATION(3, "Edit a Reservation"),
    DELETE_A_RESERVATION(4, "Delete a Reservation");

    private final int value;
    private final String message;

    MainMenuOption(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public static MainMenuOption fromValue(int value) {
        for (MainMenuOption option : MainMenuOption.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        return EXIT;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
