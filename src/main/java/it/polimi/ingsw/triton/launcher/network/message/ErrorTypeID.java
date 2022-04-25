package it.polimi.ingsw.triton.launcher.network.message;

public enum ErrorTypeID {
    USERNAME_ALREADY_CHOSEN("This username is already used!"),
    WRONG_PLAYERS_NUMBER("The number of players must be 2 or 3!"),
    FORBIDDEN_USERNAME("Username is not correct!"),
    FULL_LOBBY("Lobby is full!");


    private final String description;

    ErrorTypeID(final String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
