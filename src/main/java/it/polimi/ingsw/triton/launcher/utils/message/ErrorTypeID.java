package it.polimi.ingsw.triton.launcher.utils.message;

public enum ErrorTypeID {
    USERNAME_ALREADY_CHOSEN("This username is already used!"),
    WRONG_PLAYERS_NUMBER("The number of players must be 2 or 3!"),
    FORBIDDEN_USERNAME("Username is not correct!"),
    FULL_LOBBY("Lobby is full!"),
    WRONG_USERNAME("This username doesn't exists!"),
    ASSISTANTCARD_ALREADY_CHOSEN("This assistant card is already used!"),
    TOO_MANY_MOTHERNATURE_STEPS("The number of steps exceeds the maximum possible!"),
    NULL_COLOR("Color cannot be null!"),
    EMPTY_BAG("The bag is empty; a student cannot be drawn!"),
    EMPTY_GENERAL_COIN_SUPPLY("The general coin supply is empty! You won't receive the coin!"),
    CLOUD_TILE_ALREADY_CHOSEN("The selected cloud tile is already selected!"),
    NOT_ENOUGH_COINS("You don't have enough coins to buy this character card!");

    private final String description;

    ErrorTypeID(final String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
